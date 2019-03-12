string="$1"

nasm -f elf $string
gcc -m32 -o ${string/.asm/} ${string/asm/o}
./${string/.asm/}