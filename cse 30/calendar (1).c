/**
 * CSE 29: Systems Programming and Software Tools
 * Spring Quarter 2025
 * Programming Assignment 2
 *
 * calendar.c
 * @file This file contains the implementation of functions for
 * the calendar program that students implement.
 *
 * Author: CSE 29 Spring 2025 PA Team
 * April 2025
 */

/**
 * These are the only imports you are allowed to use.
 * Do not include any other libraries or headers.
 */

#define _GNU_SOURCE // So that you can use strcasestr
#include "calendar.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

// Refer to calendar.h for the required behaviors of each function
static int next_event_id = 1;

int add_event(week_t *week, time_t start_time, time_t end_time, char *name) {

    // finds which day the event is
    int day_index = -1;
    for (int i = 0; i < DAYS_IN_WEEK; i++){
        if( same_date(start_time, week->days[i].date)){
            day_index = i;
            break;
        }
    }
    if(day_index == -1){
        //if the event is out of bounds
        return -1;
    }

    //checking time coflicts
    day_t *day = &week->days[day_index];
    event_t *curr = day->events;

    while (curr !=NULL){
       // if new event overlaps return 1
        if (!(end_time <= curr->start_time || start_time >= curr->end_time)){
            return -1;
        }
        curr = curr->next;
    }
    
    //create and fill in the event
    event_t *new_event = malloc(sizeof(event_t));
    new_event->id = next_event_id++;
    new_event-> start_time = start_time;
    new_event-> end_time = end_time;
    new_event-> name = strdup(name); //makes a copy of the string
    new_event->next=NULL;

    // put in order
    curr = day->events;
    event_t *prev = NULL;
    while (curr && curr->start_time < new_event->start_time){
        prev = curr;
        curr = curr->next;
    }

    if(prev == NULL){
        new_event->next = day->events;
        day->events = new_event;
    }
    else{
        new_event->next = prev->next;
        prev->next = new_event;
    }

    //update count

    day->num_events++;

    //returnID
    return new_event->id;
}

int remove_event(week_t *week, int id) {
     for (int i = 0; i < DAYS_IN_WEEK; i++){
         day_t *day = &week->days[i];
         event_t *curr = day->events;
         event_t *prev = NULL;

        while(curr != NULL){
            if (curr->id == id){
                //found now remove
                if(prev==NULL){
                    //removing head
                    day->events = curr->next;                   
                }
                else{

                    //removing middle or end
                    prev->next = curr -> next;
                }
                //free memory
                free(curr->name);
                free(curr);
                day->num_events--;

                return 0;
            }

            prev = curr;
            curr = curr->next;
        }
     }

     //was not found
     return -1;
}

int search_event(week_t *week, const char *query, event_t *results[]) {
    int count = 0;
    
    for (int i = 0; i < DAYS_IN_WEEK; i++){
        event_t *curr = week->days[i].events;

        while (curr != NULL && count < 10){
            if (strcasestr(curr->name, query)){
                results[count++] = curr;
            }
            curr = curr->next;
       }
    }

    return count;

}

int reschedule_event(week_t *week, int id, time_t start_time, time_t end_time) {
    event_t *target = NULL;
    int old_day = -1;

    // find the event
    for(int i = 0; i< DAYS_IN_WEEK; i++){
        event_t *curr = week->days[i].events;
        event_t *prev = NULL;
        while (curr){
            if(curr ->id == id){
                target = curr;
                old_day = i;

                //unlink from the list
                if(prev == NULL){
                  week->days[i].events = curr->next;
                }

               else{
                 prev->next = curr ->next;
               }

                week->days[i].num_events--;
                break;

          }
          prev = curr;
          curr = curr->next;
      }
      if (target) break;
  }

    if (!target)return 1; //not found

    //checking where new time belongs

    int new_day = -1;
    for (int i = 0; i< DAYS_IN_WEEK; i++){
        if(same_date(start_time, week->days[i].date)){
            new_day = i;
            break;
        }
    }

    if(new_day == -1){
        // we got a invalid date -we got to put it back
        target ->next = week ->days[old_day].events;
        week->days[old_day].events = target;
        week->days[old_day].num_events++;
        return -1;
    }
    // now checking for conflits with new day
    event_t *curr = week->days[new_day].events;
    while (curr){
        if(!(end_time <= curr->start_time || start_time >= curr->end_time)){
            // conflict - we restore and return
            target->next = week->days[old_day].events;
            week->days[old_day].events = target;
            week->days[old_day].num_events++;
            return -1;

        }
        curr = curr->next;
    }
   
    //update time
    target->start_time = start_time;
    target->end_time = end_time;

    //reinsert in sorted 
    curr = week->days[new_day].events;
    event_t *prev = NULL;

    while(curr && curr->start_time < target-> start_time){
        prev = curr;
        curr = curr-> next;
    }

    if ( prev == NULL){
        target->next = week->days[new_day].events;
        week->days[new_day].events = target;
    }
    else{
        target->next = prev->next;
        prev->next = target;
    }
    week->days[new_day].num_events++;
    return 0;
}

int duplicate_event(week_t *week, int id, int day) {
    event_t *original =NULL;
    int original_day = -1;

    //find original event
    for (int i = 0; i< DAYS_IN_WEEK; i++){

        event_t *curr = week->days[i].events;
        while(curr){
            if(curr->id == id){
                original = curr;
                original_day = i;
                break;
            }
            curr = curr->next;
        }
        if(original) break;
    }

    if (!original) return -2; // ID not found
    if(day == original_day) return -1; // cant dupe on same date

    day_t *target_day = &week->days[day];

    //get new start end time on date
    time_t new_start = combine_date_time(target_day->date, original->start_time);
    time_t new_end = combine_date_time(target_day->date, original->end_time);

    //check for conflicts
    event_t *curr = target_day->events;
    while(curr){
        if(!(new_end <= curr->start_time || new_start >= curr->end_time)){
            return -1; //conflict
        }

        curr = curr->next;
    }

    // create new event
    event_t *copy = malloc(sizeof(event_t));
    copy->id = next_event_id++;
    copy->start_time = new_start;
    copy->end_time = new_end;
    copy->name = strdup(original->name);
    copy->next = NULL;

    // insert itno taget day om sorted order
    curr = target_day->events;
    event_t *prev = NULL;
    while (curr && curr-> start_time<new_start){
        prev = curr;
        curr = curr->next;
    }

    if(prev == NULL){
        copy->next = target_day->events;
        target_day->events = copy;       
    }
    else{
        copy->next = prev->next;
        prev->next = copy;
    }

    target_day->num_events++;

    return copy->id;
}

void free_week(week_t *week) {   
    for (int i = 0; i < DAYS_IN_WEEK; i++){
        day_t *day = &week->days[i];
        event_t *curr = day->events;

        while(curr !=NULL){
            event_t *next = curr -> next; // saving next potiner
            free(curr->name); // freeing dupe name
            free(curr);  // freeing event
            curr = next; // next
       }

  // clean reset
        day->events = NULL;
        day->num_events = 0;
    }
}
