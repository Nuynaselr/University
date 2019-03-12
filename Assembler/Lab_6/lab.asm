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

global main
extern printf

section .bss
    temp resd 1
    answer resd 1

section .data
    param_a dd 4.1
    param_b dd 1.5

    const_a dd -53.0
    const_b dd 4.0
    const_c dd 1.0

    format_text dd ' %s', 0x0a
    format_float dd ' %g ', 0x0a

section .text
main:

    fld dword [const_a]
    fdiv dword [param_a]

    fld dword [const_b]
    fld dword [param_a]
    fmul
    
    fld dword [param_c]
    fsub
    fsub

    fld dword [param_a]
    fld dword [param_b]
    fmul

    fld dword [const_c]
    fadd
    fmul

    fstp qword [answer]
    DOUBLE_PRINT dword [answer + 4], dword [answer]
    
