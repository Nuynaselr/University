;(-53/a + d-4*a)(1+a*b)

%macro DOUBLE_PRINT 2
    pushad

    push %1
    push %2
    push format_float
    call printf

    pop dword [temp]
    pop dword [temp]
    pop dword [temp]

    popad
%endmacro

%macro PRINT 2      
    pushad

    push %1             
    push %2
    call printf
    
    pop dword [temp]                                            
    pop dword [temp]

    popad
%endmacro

global main
extern printf

section .bss
    temp resd 1

section .data
    answer_mess dw "Hey yo. IT is adngel answer: "

    check dq 0.0


    param_a dq 1.0
    param_b dq 1.0
    param_c dq 1.0

    param_x dq 2.0
    param_one dq -1.0

    answer dq 0.0
    param_esp dq 0.0001

    format_text dd ' %s ', 0x0a
    format_textnl dd ' %s', 0x0a
    format_number dd ' %2d', 0x0a     
    format_float dd ' %g ', 0x0a

section .text
main:
    ; чтобы не делать лишних действий сразу вычетаем единицу из x и работаем с ним
    ; x = x - 1
    ; сохраняем в param_x, где хранится начальное значения x, но уменьшенное на 1 и работаем с ним
    ; в param_b хранится x - 1 в какой-то степени
    fld qword [param_x]
    fld qword [param_one]
    fadd

    fstp qword [param_b]

    fld qword [param_b]
    fstp qword [param_x] 


loopt:
    ; вычисляется сама формула
    fld qword [param_b]
    fld qword [param_c]
    fdiv

    fld qword [param_a]
    fmul
    
    fstp qword [check]
    fstp st0 ; чистка 

    fld qword [check]

    fld qword [answer]
    fadd
    fstp qword [answer]
    fstp st0 ; чистка 
    
    ; умножаем param_a на -1 (-1 в степени n)
    fld qword [param_a]
    fld qword [param_one]
    fmul
    fstp qword [param_a]
    fstp st0 ; чистка 

    ; прибавляем 1 к param_c (он же знаменатель)
    fld qword [param_c]
    fld qword [param_one]
    fsub
    fstp qword [param_c]
    fstp st0 ; чистка 
    
    ; умножаем param_b на x (он же числитель/ (x + 1) в степени n + 1)
    fld qword [param_b]
    fld qword [param_x]
    fmul
    fstp qword [param_b]
    fstp st0 ; чистка 

    fld qword [param_esp]
    fld qword [check]
    fabs
    fcom
    fstsw ax
    sahf
    fstp st0 ; чистка
    
    ja loopt
    jbe less

less:
    PRINT answer_mess, format_text
    DOUBLE_PRINT dword [answer + 4], dword [answer]
    ret