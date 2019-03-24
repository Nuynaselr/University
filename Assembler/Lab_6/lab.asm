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


    const_a1 dd -1.0
    
    const_a2 dd 255.0

    const_a3 dd 2.0
    const_b3 dd -9.0

    format_text dd ' %s', 0x0a
    format_number dd ' %2d', 0x0a     
    format_float dd ' %g ', 0x0a

section .text
main:
    fld dword [param_b]
    fld dword [param_a]

    fcom
    fstsw ax
    sahf

    ja greater
    jb less
    jz equal
    
    equal:  fstp st0
            fstp st0
            fld dword [const_a2]
            fstp qword [answer]
            call print
            ret

    greater:    fld dword [param_b]
                fld dword [param_b]
                fmul ; b^2

                fld dword [param_a]
                fmul ; b^2 * a

                fld dword [const_a1]
                fadd ; b^2 * a - 1

                fld dword [param_a]
                fdiv ; (b^2 * a - 1)/a
                fstp qword [answer]
                call print
                ret
                


    less:   fstp st0
            fstp st0
            fld dword [param_a]
            fld dword [const_a3]
            fmul ; 2*a

            fld dword [const_b3]
            fadd ; 2*a-9

            fld dword [param_b]
            fdiv ; (2*a-9) / b
            fstp qword [answer]
            call print
            ret


    print:  DOUBLE_PRINT dword [answer + 4], dword [answer]
            ret

   
    
    
