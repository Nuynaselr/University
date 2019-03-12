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


global main
extern printf

section .bss
    temp resd 1

section .data
    array dd 3, -1, 2, -3, -7
    array_size dd 5

    format_number dd ' %2d', 0x0a     
    format_text dd ' %s', 0x0a

section .text
main:
    mov esi, array
    mov ecx, [array_size]
    
    lp: mov ebx, [esi]
        call greater
        add esi, 4
        loop lp 

    greater: cmp ebx, 0
             jg operation
             ret
    
    operation: mov ebx, [esi]
               imul ebx, ebx
               PRINT ebx, format_number
               mov ebx, 0
               ret
