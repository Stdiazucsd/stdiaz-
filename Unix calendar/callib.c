/**
 * CSE 29: Systems Programming and Software Tools
 * Spring Quarter 2025
 * Programming Assignment 2
 *
 * callib.c
 * @file This file contains the implementation of functions for
 * the calendar program that are provided to students.
 *
 * Author: CSE 29 Spring 2025 PA Team
 * April 2025
 */

#define _GNU_SOURCE
#include "calendar.h"
#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

// Do not modify the code below this line!
//------------------------------------------------------------------------------

void print_event(event_t *event, const char *prefix) {
    char time_buf[30]; // Buffer to hold formatted time string

    printf("%s| %-*s: %d\n", prefix, LABEL_WIDTH, "Event ID", event->id);
    printf("%s| %-*s: %s\n", prefix, LABEL_WIDTH, "Event Name",
           event->name ? event->name : "(null)");

    strftime(time_buf, sizeof(time_buf), TIME_FORMAT,
             localtime(&event->start_time));
    printf("%s| %-*s: %s\n", prefix, LABEL_WIDTH, "Start Time", time_buf);

    strftime(time_buf, sizeof(time_buf), TIME_FORMAT,
             localtime(&event->end_time));
    printf("%s| %-*s: %s\n", prefix, LABEL_WIDTH, "End Time", time_buf);
}

void print_day(day_t *day) {
    char date_buf[11];
    strftime(date_buf, sizeof(date_buf), DATE_FORMAT, localtime(&day->date));
    printf("Date: (%s), Number of Events: (%d)\n", date_buf, day->num_events);
    event_t *current_event = day->events;
    while (current_event != NULL) {
        print_event(current_event, "  ");
        current_event = current_event->next;
    }
}

void print_week(week_t *week) {
    printf("Week:\n");
    for (int i = 0; i < DAYS_IN_WEEK; i++) {
        print_day(&week->days[i]);
    }
}

void print_day_aligned(day_t *day) {
    printf("Date: (%s), Number of Events: (%d)\n", ctime(&day->date),
           day->num_events);
    event_t *current_event = day->events;

    for (int minutes = 0; minutes < MINUTES_PER_DAY; minutes += MINUTE_QUANTA) {
        int hour = minutes / MINUTES_PER_HOUR;
        int min = minutes % MINUTES_PER_HOUR;

        // Example line:
        // 15:30 || 042 Meeting With Bob
        printf("%02d:%02d || ", hour, min);

        while (current_event != NULL) {
            time_t interval_start = day->date + minutes * 60;
            time_t interval_end = interval_start + MINUTE_QUANTA * 60;

            // Check if the event overlaps with the current time interval
            if (current_event->start_time < interval_end &&
                current_event->end_time >= interval_start) {
                int event_id = current_event->id;
                char *event_name = current_event->name;
                printf("%03d %s ", event_id, event_name);
                break;
            }
            current_event = current_event->next;
        }
        printf("\n");
        current_event = day->events; // Reset to the start of the event list
    }
}

void print_week_aligned(week_t *week) {
    int total_events = 0;
    for (int i = 0; i < DAYS_IN_WEEK; i++) {
        total_events += week->days[i].num_events;
    }
    printf("Total Events: %d\n", total_events);

    printf("      |");
    char date_buf[11];
    for (int day_num = 0; day_num < DAYS_IN_WEEK; day_num++) {
        strftime(date_buf, sizeof(date_buf), DATE_FORMAT,
                 localtime(&week->days[day_num].date));
        printf("| %-*s", LABEL_WIDTH + 5, date_buf);
    }
    printf("\n");

    for (int minutes = 0; minutes < MINUTES_PER_DAY; minutes += MINUTE_QUANTA) {
        int hour = minutes / MINUTES_PER_HOUR;
        int min = minutes % MINUTES_PER_HOUR;

        printf("%02d:%02d |", hour, min);

        for (int day_num = 0; day_num < DAYS_IN_WEEK; day_num++) {
            event_t *current_event = week->days[day_num].events;
            int event_printed = 0;
            while (current_event != NULL) {
                time_t interval_start = week->days[day_num].date + minutes * 60;
                time_t interval_end = interval_start + MINUTE_QUANTA * 60;

                if (current_event->start_time < interval_end &&
                    current_event->end_time >= interval_start) {
                    int event_id = current_event->id;
                    char *event_name = strdup(current_event->name);
                    if (strlen(event_name) > LABEL_WIDTH)
                        event_name[LABEL_WIDTH] = '\0';
                    printf("| %03d %-*s ", event_id, LABEL_WIDTH, event_name);
                    free(event_name);
                    event_printed = 1;
                    break;
                }
                current_event = current_event->next;
            }
            if (!event_printed) {
                printf("|     %-*s ", LABEL_WIDTH, "");
            }
        }
        printf("\n");
    }
}

week_t *calendar = NULL;

static time_t increment_day(time_t time) {
    // Some days are 23 or 25 hours long due to DST, so this method is needed
    struct tm *tm = localtime(&time);
    ++tm->tm_mday;
    tm->tm_isdst = -1;
    return mktime(tm);
}

void setup_calendar(time_t first_day) {
    calendar = malloc(sizeof(week_t));
    if (calendar == NULL) {
        perror("setup_calendar");
        exit(EXIT_FAILURE);
    }
    time_t current_day = first_day;
    for (int day = 0; day < DAYS_IN_WEEK; ++day) {
        calendar->days[day].date = current_day;
        calendar->days[day].events = NULL;
        calendar->days[day].num_events = 0;
        current_day = increment_day(current_day);
    }
}

int same_date(time_t time1, time_t time2) {
    struct tm first = *localtime(&time1), second = *localtime(&time2);
    return first.tm_yday == second.tm_yday && first.tm_year == second.tm_year;
}

time_t combine_date_time(time_t date, time_t time) {
    struct tm date_cmp = *localtime(&date);
    struct tm time_cmp = *localtime(&time);

    date_cmp.tm_hour = time_cmp.tm_hour;
    date_cmp.tm_min = time_cmp.tm_min;
    date_cmp.tm_sec = time_cmp.tm_sec;
    date_cmp.tm_isdst = -1;
    return mktime(&date_cmp);
}

static int read_cal(const char *filename) {
    FILE *file = fopen(filename, "r");
    if (!file) {
        perror("Error opening file");
        return 1;
    }

    calendar = NULL;
    char line[MAX_CAL_LINE_LENGTH];
    int day_index = 0;

    while (fgets(line, sizeof(line), file)) {
        char *line_ptr = line;
        char *day_str = strsep(&line_ptr, "\n");
        time_t date = (time_t)atol(strsep(&day_str, ","));
        int num_events = atoi(strsep(&day_str, ","));

        if (calendar == NULL) {
            setup_calendar(date);
        }

        for (int i = 0; i < num_events; i++) {
            fgets(line, sizeof(line), file);
            line_ptr = line;
            char *event_str = strsep(&line_ptr, "\n");

            char *name = strsep(&event_str, ",");
            int len = 0;
            while (name[len] != '\0')
                len++;
            time_t start_time = (time_t)atol(strsep(&event_str, ","));
            time_t end_time = (time_t)atol(strsep(&event_str, ","));

            add_event(calendar, start_time, end_time, name);
        }
        day_index++;
    }
    fclose(file);
    return 0;
}

static void write_cal(const char *filename) {
    FILE *file = fopen(filename, "w");
    if (!file) {
        perror("Error opening output file");
        exit(EXIT_FAILURE);
    }

    for (int i = 0; i < DAYS_IN_WEEK; i++) {
        day_t day = calendar->days[i];
        fprintf(file, "%ld,%d\n", (long)day.date, day.num_events);
        event_t *e = day.events;
        while (e) {
            fprintf(file, "%s,%ld,%ld\n", e->name, (long)e->start_time,
                    (long)e->end_time);
            e = e->next;
        }
    }

    fclose(file);
}

static void shell_show_help(void) {
    puts("Available shell commands:\n"
         "  (w)rite      write a calendar to a .unical file\n"
         "  (c)reate     create calendar event\n"
         "  (r)emove     remove calendar event\n"
         "  (s)earch     search for calendar event by name\n"
         "  (m)ove       reschedule a calendar event\n"
         "  (d)uplicate  duplicate a calendar event into a different day\n"
         "  (p)rint      display the stored calendar graphically\n"
         "  (l)ist       list the events in the calendar\n"
         "  (q)uit       exit the program\n"
         "  (?)          show this message");
}

static char *readline(const char *prompt) {
    printf("%s", prompt);
    printf(" >");
    fflush(stdout);
    char *line = NULL;
    size_t linecap = 0;
    if (getline(&line, &linecap, stdin) <= 0) {
        free(line);
        return NULL;
    } else {
        // To strip the newline off
        line[strlen(line) - 1] = '\0';
        return line;
    }
}

#define CHECK_EOF(str)                                                         \
    if ((str) == NULL) {                                                       \
        fprintf(stderr, "EOF");                                                \
        exit(1);                                                               \
    }

int read_time(const char *prompt, time_t *out) {
    const time_t sample_time = 1745456400;
    char timebuf[24];

    strftime(timebuf, sizeof(timebuf), TIME_FORMAT, localtime(&sample_time));
    printf("Example time format: %s\n", timebuf);
    char *time_expr = readline(prompt);
    CHECK_EOF(time_expr)

    struct tm time;
    time.tm_sec = 0;
    time.tm_isdst = -1;
    if (strptime(time_expr, TIME_FORMAT, &time) == NULL) {
        puts("Failed to parse time input");
        free(time_expr);
        return 0;
    }
    *out = mktime(&time);
    free(time_expr);
    return 1;
}

int read_interval(time_t *start, time_t *end) {
    if (!read_time("Enter the start time of the event", start) ||
        !read_time("Enter the end time of the event", end)) {
        return 0;
    }
    if (!same_date(*start, *end)) {
        puts("Invalid inputs: Start and end time are not on the same day");
        return 0;
    }
    if (*start >= *end) {
        puts("Invalid inputs: Start time must be before end time");
        return 0;
    }
    return 1;
}

static void shell_load(void) {
    FILE *fp = popen("ls *.unical", "r");
    if (fp == NULL) {
        perror("popen failed");
        return;
    }

    char buffer[256];
    int files_found = 0;
    while (fgets(buffer, sizeof(buffer), fp) != NULL) {
        printf("\t> %s", buffer);
        files_found = 1;
    }
    if (!files_found) {
        printf("\t> No .unical files found.\n");
    } else {
        printf("Detected the following .unical files:\n");
    }
    pclose(fp);

    char *file_path =
        readline("Enter the path to the .unical file, or press Enter to "
                 "initialize an empty calendar");
    CHECK_EOF(file_path)
    if (calendar != NULL) {
        free_week(calendar);
        calendar = NULL;
    }
    if (file_path[0] == '\0') {
        do {
            char *first_date_str =
                readline("Enter the date of the first day in "
                         "the calendar in the form YYYY-MM-DD");
            struct tm tm;
            tm.tm_hour = 0;
            tm.tm_min = 0;
            tm.tm_sec = 0;
            tm.tm_isdst = -1;
            if (strptime(first_date_str, DATE_FORMAT, &tm) == NULL) {
                puts("Invalid format");
            } else {
                setup_calendar(mktime(&tm));
            }
            free(first_date_str);
        } while (calendar == NULL);
    } else if (read_cal(file_path) != 0) {
        free(file_path);
        exit(EXIT_FAILURE);
    }
    puts("Calendar initialized");
    printf("The first day of the calendar starts at %s\n",
           ctime(&calendar->days[0].date));
    free(file_path);
}

static void shell_write(void) {
    char *file_path = readline("Enter the path of the file to write to");
    CHECK_EOF(file_path)
    if (file_path[0] == '\0') {
        puts("Not accepting empty filename");
        return;
    }
    write_cal(file_path);
    puts("Calendar written");
    free(file_path);
}

static void shell_create(void) {
    char *name = readline("Enter the name of the event");
    CHECK_EOF(name)

    int event_id;
    time_t start, end;
    if (!read_interval(&start, &end)) {
        free(name);
        return;
    }
    if ((event_id = add_event(calendar, start, end, name)) < 0) {
        puts("Failed to add event (out of range or conflict found)");
    } else {
        printf("Event created with ID %d\n", event_id);
    }
    free(name);
}

static void shell_search(void);
static void shell_remove(void) {
    char *id = readline("Enter the ID of the event to remove. If you don't "
                        "know, enter \"search\" to perform a search by name");
    CHECK_EOF(id)

    if (strcmp(id, "search") == 0) {
        free(id);
        shell_search();
        shell_remove();
        return;
    }
    int idnum;
    if (sscanf(id, "%d", &idnum) != 1) {
        puts("Failed to parse input as an integer");
        free(id);
        return;
    }

    if (remove_event(calendar, idnum) == 0) {
        printf("Successfully removed event %d\n", idnum);
    } else {
        printf("Event %d not found\n", idnum);
    }
    free(id);
}

static void shell_search(void) {
    char *query =
        readline("Enter a part of the name of the event to search for");
    CHECK_EOF(query);

    if (query[0] == '\0') {
        puts("Empty search queries are not accepted");
        free(query);
        return;
    }
    event_t *results[10];
    int results_found = search_event(calendar, query, results);
    for (int i = 0; i < results_found; i++) {
        char startbuf[20];
        strftime(startbuf, sizeof(startbuf), TIME_FORMAT,
                 localtime(&results[i]->start_time));
        printf("ID %d: %s (starts %s)\n", results[i]->id, results[i]->name,
               startbuf);
    }
    if (results_found == 0) {
        puts("No results found");
    }

    free(query);
}

static void shell_move(void) {
    char *id = readline("Enter the ID of the event to reschedule. If you don't "
                        "know, enter \"search\" to perform a search by name");
    CHECK_EOF(id)

    if (strcmp(id, "search") == 0) {
        free(id);
        shell_search();
        shell_move();
        return;
    }
    int idnum;
    if (sscanf(id, "%d", &idnum) != 1) {
        puts("Failed to parse input as an integer");
        free(id);
        return;
    }
    free(id);

    time_t start, end;
    if (!read_interval(&start, &end)) {
        return;
    }
    int result = reschedule_event(calendar, idnum, start, end);
    if (result == 0) {
        printf("Successfully rescheduled event %d\n", idnum);
    } else if (result == 1) {
        printf("Event %d not found\n", idnum);
    } else if (result == -1) {
        printf("Failed to reschedule: Date out of range or conflict found\n");
    } else {
        printf("reschedule_event returned unknown code: %d\n", result);
    }
}

static void shell_duplicate(void) {
    char *id = readline("Enter the ID of the event to duplicate. If you don't "
                        "know, enter \"search\" to perform a search by name");
    CHECK_EOF(id)

    if (strcmp(id, "search") == 0) {
        free(id);
        shell_search();
        shell_duplicate();
        return;
    }
    int idnum;
    if (sscanf(id, "%d", &idnum) != 1) {
        puts("Failed to parse input as an integer");
        free(id);
        return;
    }
    free(id);

    puts("Days of the calendar:");
    for (int day = 0; day < DAYS_IN_WEEK; day++) {
        char timebuf[20];
        strftime(timebuf, sizeof(timebuf), DATE_FORMAT,
                 localtime(&calendar->days[day].date));
        printf("%d. %s\n", day, timebuf);
    }
    char *daystr = readline("Enter the day to duplicate the event into");
    CHECK_EOF(daystr);
    int day;
    if (sscanf(daystr, "%d", &day) != 1) {
        puts("Failed to parse input as an integer");
        free(daystr);
        return;
    }
    if (day < 0 || day >= DAYS_IN_WEEK) {
        puts("Received day is out of range");
        free(daystr);
        return;
    }
    free(daystr);

    int result = duplicate_event(calendar, idnum, day);
    if (result == -1) {
        puts("Failed due to time conflict");
    } else if (result == -2) {
        printf("Failed to find an event with ID %d\n", idnum);
    } else {
        printf("Successfully duplicated event %d into ID %d\n", idnum, result);
    }
}

#ifndef CALLIB_NOMAIN

int main(int argc, char *argv[]) {
    if (argc == 2) {
        printf("Reading calendar from %s...\n", argv[1]);
        read_cal(argv[1]);
    } else {
        shell_load();
    }
    while (1) {
        char *input = readline("unical");
        if (input == NULL) {
            free_week(calendar);
            return 0;
        }
        switch (tolower(input[0])) {
        case 'w':
            shell_write();
            break;
        case 'c':
            shell_create();
            break;
        case 'r':
            shell_remove();
            break;
        case 's':
            shell_search();
            break;
        case 'm':
            shell_move();
            break;
        case 'd':
            shell_duplicate();
            break;
        case 'q':
            free_week(calendar);
            free(input);
            return 0;
        case 'p':
            print_week_aligned(calendar);
            break;
        case 'l':
            print_week(calendar);
            break;
        default:
            shell_show_help();
        }
        free(input);
    }
}

#endif
