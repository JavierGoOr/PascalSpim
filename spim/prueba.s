.data
	a2: .word 'a', 'b', 'c'
     	a: .space 1568   
.text
        .globl main
main:	li $t0, 480
	la $t1, a
	add $t0, $t0, $t1
	l.d $f0, ($t0)
	li $v0, 10
        syscall