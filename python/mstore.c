#include <stdio.h>

void main() {
    // char **sptr;
    // char *s = "chenxiangyu";
    // sptr = &s;
    // printf("%s\n", &s[5]);
    // printf("s = %p\n", s);
    // printf("&s[0] = %p\n", &s[0]);
    // printf("&s = %p\n", &s);
    // printf("sptr = %p\n", sptr);

    char a[] = {'a', 'b', 'c', 'd', '\0'};
    printf("%c\n", *a + 1);
}