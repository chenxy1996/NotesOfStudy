#include<stdio.h>
void swap(int, int);
void swapByPtrs(int *, int *);
void swapByRefs(int &, int &);

void swap(int arg1, int arg2) {
    printf("\nEnter swap Function:\n");
    printf("Address of arg1: %p\n", &arg1);
    printf("Address of arg2: %p\n", &arg2);
    printf("arg1 = %d\n", arg1);
    printf("arg2 = %d\n", arg2);
    int temp = arg1;
    arg1 = arg2;
    arg2 = temp;
    printf("After Swap: arg1 = %d\n", arg1);
    printf("After Swap: arg2 = %d\n", arg2);
    printf("Leaf swap Function.\n\n");
}

void swapByPtrs(int *arg1, int *arg2) {
    printf("\nEnter swapByPtrs Function!\n");
    printf("Address of arg1: %p\n", &arg1);
    printf("Address of arg2: %p\n", &arg2);
    printf("arg1 = %p\n", arg1);
    printf("arg2 = %p\n", arg2);
    int temp = *arg1;
    *arg1 = *arg2;
    *arg2 = temp;
    printf("After Swap: arg1 = %p\n", arg1);
    printf("After Swap: arg2 = %p\n", arg2);
    printf("Leaf swapByPtrs Function.\n\n");
}

void swapByRefs(int &arg1, int &arg2) {
    printf("\nEnter swapByRefs Function!\n");
    printf("Address of arg1: %p\n", &arg1);
    printf("Address of arg2: %p\n", &arg2);
    printf("arg1 = %d\n", arg1);
    printf("arg2 = %d\n", arg2);
    int temp = arg1;
    arg1 = arg2;
    arg2 = temp;
    printf("After Swap: arg1 = %d\n", arg1);
    printf("After Swap: arg2 = %d\n", arg2);
    printf("Leaf swapByRefs Function.\n\n");
}

void change(int *a) {
    printf("enter change!\n");
    printf("a = %p\n", a);
    int temp = 5;
    printf("after temp assignment: a = %p\n", a);
    printf("temp = %d\n", temp);
    a = &temp;
    *a = 7;
    printf("temp = %d\n", temp);
    printf("leave change!\n");
}

int main() {
    // int a= 3;
    // int b = 4;
    // printf("%p\n", &a);
    // printf("%p\n", &b);
    // printf("a = %d; b = %d\n", a, b);
    // swapByPtrs(&a, &b);
    // printf("%p\n", &a); 
    // printf("%p\n", &b);
    // printf("a = %d; b = %d\n", a, b);
    // swap(a, b);
    // printf("a = %d; b = %d\n", a, b);
    // return 0;

    // int a = 5;
    // printf("%p\n", &a);
    // change(&a);
    // printf("%p\n", &a);
    // printf("a = %d\n", a);

    int a = 3;
    int b = 4;
  	printf("Address of a: %p\n", &a);
    printf("Address of b: %p\n", &b);
    printf("a = %d\n", a);
    printf("b = %d\n", b);
    
    swapByPtrs(&a, &b);
    
    printf("After Swap: Address of a: %p\n", &a);
    printf("After Swap: Address of b: %p\n", &b);
    printf("After Swap: a = %d\n", a);
    printf("After Swap: b = %d\n", b);
}