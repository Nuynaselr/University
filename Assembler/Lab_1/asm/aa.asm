; (-53/a + d-4*a)/(1 +a*b);

%macro PRINT 2      
    push eax                                                    ;во время вызова printf следующие регистры сбросятся. Что бы избежать этого, временно уберем их в стек
    push ebx
    push ecx

    push %1             
    push %2
    call printf
    
    pop dword [temp]                                            ;что бы достать из стека переданные параметры, будем убирать их в temp
    pop dword [temp]
    pop ecx
    pop ebx
    pop eax
%endmacro

%macro CHECK_ZERO 1
    cmp %1, 0
    je div_be_zero
%endmacro

global main
extern printf
extern exit

section .bss                                                    ;секция в которой объявляются не инициализированные участки памяти (переменные)
    temp resd 1         

section .data
    param_a dd 5
    param_b dd 7
    param_d dd 6

    mess_zero dd "Error: number is zero"
    inf_message dd "Error: Some number doesn't fit in integer"

    format_number dd ' %2d', 0x0a     
    format_text dd ' %s', 0x0a

section .text
main:
    mov eax, -53                                                ; eax = -53
    cdq
    CHECK_ZERO dword [param_a] 
    idiv dword [param_a]                                        ; eax / 1 = -53 / 1 = 1
    jo overflow

    mov ebx, 4                                                  ; ebx = 4
    imul ebx, dword [param_a]                                   ; ebx * 1 = 4 * 1 = 4
    jo overflow
    mov edx, dword [param_d]
    sub edx, ebx                                                ; edx - ebx = 5 - 4 = 1
    jo overflow

    add eax, edx                                                ; eax + edx = -53 + 1 = -52
    jo overflow

    mov ebx, dword [param_a]                                    ; evx = 3
    imul ebx, dword [param_b]                                   ; ebx * 1 = 3 * 1 = 3
    jo overflow
    add ebx, 1                                                  ; ebx + 1 = 3 + 1 = 4 

    cdq
    
    idiv ebx                                                    ; eax / ebx = -52 / 4 = -13
    jo overflow
    
    PRINT edx, format_number                                    ; вывод данных

    call exit

div_be_zero:
    push ebp
    mov ebp, esp
    PRINT mess_zero, format_text
    mov esp, ebp
    pop ebp
    call exit

overflow:
    push ebp
    mov ebp, esp
    PRINT inf_message, format_text
    mov esp, ebp
    pop ebp
    call exit