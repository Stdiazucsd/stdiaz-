// clang-format Language: C
/**
 * CSE 29: Systems Programming and Software Tools
 * Spring Quarter 2025
 * Programming Assignment 2
 *
 * calendar.h
 * @file This header file defines the functions and structs for our calendar
 * program.
 *
 * Author: CSE 29 Spring 2025 PA Team
 * April 2025
 *
 */

#ifndef CALENDAR_H
#define CALENDAR_H

#include <time.h> // contains definitions for Unix time

#define DAYS_IN_WEEK 7
#define MINUTES_PER_HOUR 60
#define HOURS_PER_DAY 24
#define MINUTES_PER_DAY (MINUTES_PER_HOUR * HOURS_PER_DAY)
#define LABEL_WIDTH 10
#define MINUTE_QUANTA 15
#define TIME_FORMAT "%Y-%m-%d %H:%M"
#define DATE_FORMAT "%Y-%m-%d"
#define MAX_CAL_LINE_LENGTH 256

/*******************************************************************************
|                                                                              |
|                                   Structs                                    |
|                                                                              |
|******************************************************************************/

/**
 * Struct to represent an event in the calendar.
 * It contains the event name, ID, start time and end time in Unix time format,
 * and a pointer to the next event in the linked list.
 *
 * @param id The ID of the event.
 * @param name The name of the event.
 * @param start_time The start time of the event in Unix time format.
 * @param end_time The end time of the event in Unix time format.
 * @param next A pointer to the next event in the linked list.
 */
typedef struct event {
    int id;
    char *name;
    time_t start_time;
    time_t end_time;
    struct event *next;
} event_t;

/**
 * Struct to represent a day in the calendar.
 * It contains the date in Unix time format, an integer to represent the number
 * of events on that day, and a pointer to the head of the linked list of
 * events.
 *
 * @param date The date in Unix time format.
 * @param num_events The number of events on that day.
 * @param events A pointer to the head of the linked list of events.
 */
typedef struct day {
    time_t date;
    int num_events;
    event_t *events;
} day_t;

/**
 * Struct to represent the week.
 * It contains an array of days of length 7 i.e. the days of the week.
 * Each day is of type day_t.
 *
 * You may ASSUME that:
 * - For 0 <= i < 7, days[i]->date is the beginning (12:00 AM) of a day
 * - For 1 <= i < 7, days[i]->date is in the day after that of days[i-1]->date
 *   (In other words, the dates are consecutive days)
 *
 * @param days An array of days of length 7.
 */
typedef struct week {
    day_t days[DAYS_IN_WEEK];
} week_t;

/**
 * Global calendar object.
 * No need to worry about what "extern" means for now!
 * You can ignore it for this PA.
 *
 * NOTE: Your code should always use the "week" parameter instead of this
 * global variable.
 */
extern week_t *calendar;

/*******************************************************************************
|                                                                              |
|                             Function Prototypes                              |
|                                                                              |
|******************************************************************************/

// These are implemented for you in callib.c
// You should make use of them in calendar.c
// You should NEVER have to call any function declared in time.h directly!

/**
 * Check if the two given times fall within the same date in local time (PT).
 * You should use this function to determine which day to add each event to.
 *
 * Example: same_date([MAY 5 11 PM], [MAY 5 12 AM]) returns 1
 * Example: same_date([MAY 6 1 AM], [MAY 5 12 AM]) returns 0
 */
int same_date(time_t time1, time_t time2);

/**
 * Combine the date (year-month-day) portion of one time_t value with the time
 * portion (hour-minute-second) of a different time_t value. This is useful for
 * implementing reschedule_event.
 *
 * Example: combine_date_time([MAY 5 12 AM], [MAY 7 9 AM]) returns [MAY 5 9 AM]
 * by taking the date portion from the first parameter (MAY 5) with the time
 * portion of the second parameter (9 AM).
 */
time_t combine_date_time(time_t date, time_t time);

// This macro represents May 5, 2025 12:00 AM PDT in Unix time.
// You may use this macro when testing your code.
// NOTE: Your calendar should support any start date representable in Unix time.
#define MAY_5 1746428400

//------------------------------------------------------------------------------
// TODO Your task is to implement the following functions:

/**
 * Add a new event to the calendar. Find the correct day to insert the new event
 * into using the same_date function declared above.
 *
 * You may assume that the following conditions are always true:
 *  - start_time < end_time
 *  - same_date(start_time, end_time)
 *  - malloc always succeeds
 *  - name != NULL
 *  - week != NULL
 *
 * The new event's ID should be 1 + the previously issued event ID.
 * The ID of the first event added should be 1. The IDs of removed events shall
 * not be reused.
 *
 * The new event is considered "OUT OF RANGE" if it is not on the same date as
 * any of the 7 days in the week. For example, for a calendar from May 12 to May
 * 18, any event on May 11 falls out of range.
 *
 * @param week A pointer to the week to which the event will be added.
 * @param start_time The start time of the event in Unix time format.
 * @param end_time The end time of the event in Unix time format.
 * @param name The name of the event, which **should be copied** in this
 *             function.
 * @return If the time falls out of range or if there is a conflict, return -1.
 *         If the event is added successfully, return the new event's ID.
 */
int add_event(week_t *week, time_t start_time, time_t end_time, char *name);

/**
 * Remove an event from the calendar.
 *
 * @param week A pointer to the week from which the event will be removed.
 * @param id The ID of the event to be removed.
 * @return If the event is not found, return -1. If the event is removed
 *         successfully, return 0.
 */
int remove_event(week_t *week, int id);

/**
 * Search for an event in the calendar, and report up to 10 results.
 * You may assume that no more than 10 events match the given query.
 *
 * Traverse each day_t from week->days[0] to week->days[6], appending event_t
 * pointers to results as each matching event is found.
 *
 * Hint: Check out the man page for `strcasestr`.
 *
 * @param week A pointer to the week in which to search for the event.
 * @param query Part of the name of the event to be searched for.
 *              The query should match all names that *contain* it as a
 *              substring (case-insensitive).
 * @param results An array to write the search results into. Assume that the
 *                array has enough space to hold 10 event pointers.
 * @return The number of matched events.
 */
int search_event(week_t *week, const char *query, event_t *results[]);

/**
 * Reschedule an event in the calendar. Assume that the new start_time and
 * end_time will have the same date (i.e., Tue 11pm to Wed 1am is an impossible
 * input you do not need to account for), but beware that the new date may not
 * be equal to the original event's date.
 *
 * You must ensure:
 *  - No change to the ID of the event being rescheduled
 *  - No effect on the next ID issued by add_event
 *  - If rescheduling fails, the original event should not be modified
 *
 * @param week A pointer to the week in which the event will be rescheduled.
 * @param id The ID of the event to be rescheduled.
 * @param start_time The new start time of the event in Unix time format.
 * @param end_time The new end time of the event in Unix time format.
 * @return If the time falls out of range or if there is a conflict, return -1.
 *         If the event is rescheduled successfully, return 0.
 *         If no event exists with the given ID, return 1.
 */
int reschedule_event(week_t *week, int id, time_t start_time, time_t end_time);

/**
 * Duplicate an event into another day. Assume that day >= 0 and day < 7.
 *
 * If the given day is equal to the `days` index of the original event (i.e.,
 * if the user tries to duplicate an event onto itself), report a time
 * conflict by returning -1.
 *
 * You must ensure:
 *  - No change to the original event that is duplicated
 *  - No ID clashes between the newly created event and any existing event
 *  - If this function successfully duplicates the event, the next ID issued by
 *    add_event should be this function's return value + 1. In general, using
 *    this function alongside add_event should not cause any ID clashes.
 *
 * HINT: Please use the provided combine_date_time function to set the new
 * event's start and end time.
 *
 * @param week A pointer to the week in which the event will be duplicated.
 * @param id The ID of the event to be duplicated.
 * @param day The day to which the event will be duplicated, as an index into
 *            `week->days`. Assume that day >= 0 and day < 7.
 * @return If there is a time conflict, return -1.
 *         If the ID is not found, return -2.
 *         If the event is duplicated successfully, return the new event's ID.
 */
int duplicate_event(week_t *week, int id, int day);

/**
 * Free all memory allocated for the week.
 *
 * @param week A pointer to the week to be freed.
 */
void free_week(week_t *week);

#endif
