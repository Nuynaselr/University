%macro PRINT 2      
    push eax
    push ebx
    push ecx
    push edx

    push %1             
    push %2
    call printf
    
    pop dword [temp]                                            
    pop dword [temp]
    pop edx
    pop ecx
    pop ebx
    pop eax
%endmacro

%macro ZERO_CHECK 1
    cmp %1, 0
    je divide_by_zero_ex
%endmacro

global main
extern printf
extern exit

section .bss
    temp resd 1

section .data
    param_a dd 1
    param_b dd 1
    inf_message dd "Error: number is zero"
    format_number dd ' %2d', 0x0a     
    format_text dd ' %s', 0x0a

section .text
main:
    mov ebx, dword [param_a]
    mov ecx, dword [param_b]
    
    cmp ebx, ecx
    je equal
    jl less
    jg greater

    prnt:
        PRINT eax, format_number
        ret

    equal: 
        mov eax, -255                                           ; eax = -255
        jmp prnt

    less:
        imul ebx, 2                                             ; ebx = ebx * 2 (1*2 = 2)
        sub ebx, 9                                              ; ebx = ebx - 9 (2 - 9 = -7)
        
        mov eax, ebx                                            ; eax = ebx (eax = -7)

        cdq
        cmp ecx, 0
        je divide_by_zero_ex
        idiv ecx                                                ; eax /= ecx (-7/4 = 1)
        jmp prnt

    greater:
        imul ebx, ecx                                           ; ebx *= ecx
        imul ebx, dword [ebx]                                   ; ebx *= ebx (ebx = ebx^2)
        sub ebx, 1                                              ; ebx -= 1

        mov eax, ebx                                            ; eax = ebx

        cdq
        cmp ecx, 0
        je divide_by_zero_ex
        idiv dword [param_a]                                    ; eax /= param_a
        jmp prnt

    divide_by_zero_ex:
        push ebp
        mov ebp, esp
        PRINT inf_message, format_text
        mov esp, ebp
        pop ebp
        call exit