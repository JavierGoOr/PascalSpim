.data
	_aglobal:	.word 0:4
	_i:	.word 0
	w_ln:	.asciiz "\n"
	w_char:	.asciiz "\n"
	w_true:	.asciiz "true"
	w_false:	.asciiz "false"
.text
	.globl _main_
_main_: 	li $a2, 'h'
	li $a3, 0
	move $a3, $a3
	mul $a3, $a3, 4
	la $t0, _aglobal
	add $a3, $a3, $t0
	sw $a2, ($a3)
	li $a2, 'o'
	li $a3, 1
	move $a3, $a3
	mul $a3, $a3, 4
	la $t0, _aglobal
	add $a3, $a3, $t0
	sw $a2, ($a3)
	li $a2, 'l'
	li $a3, 2
	move $a3, $a3
	mul $a3, $a3, 4
	la $t0, _aglobal
	add $a3, $a3, $t0
	sw $a2, ($a3)
	li $a2, 'a'
	li $a3, 3
	move $a3, $a3
	mul $a3, $a3, 4
	la $t0, _aglobal
	add $a3, $a3, $t0
	sw $a2, ($a3)
	li $a2, 0
__while0:	li $a3, 4
	slt $a3, $a2, $a3
	beqz $a3, __whileEnd0
	sw $a2, _i
	subu $sp, $sp, 4
	sw $a2, ($sp)
	lw $a2, _i
	move $a3, $a2
	mul $a3, $a3, 4
	la $t0, _aglobal
	add $a3, $a3, $t0
	lw $a3, ($a3)
	subu $sp, $sp, 4
	sw $a3, ($sp)
	jal _writechar
	addu $sp, $sp, 4
	lw $a2, ($sp)
	addu $sp, $sp, 4
	lw $a2, _i
	li $a3, 1
	add $a3, $a2, $a3
	move $a2, $a3
	b __while0
__whileEnd0:	sw $a2, _i
	li $v0, 10
	syscall
_writeint:	subu $sp, $sp, 8
	sw $ra, 4($sp)
	sw $fp, 0($sp)
	addu $fp, $sp, 8
	lw $a2, 0($fp)
	sw $a2, -12($fp)
	subu $sp, $sp, 4
	lw $a0, -12($fp)
	li $v0, 1
	syscall
	lw $ra, -4($fp)
	move $sp, $fp
	lw $fp, -8($fp)
	jr $ra
_writeintln:	subu $sp, $sp, 8
	sw $ra, 4($sp)
	sw $fp, 0($sp)
	addu $fp, $sp, 8
	lw $a2, 0($fp)
	sw $a2, -12($fp)
	subu $sp, $sp, 4
	lw $a0, -12($fp)
	li $v0, 1
	syscall
	la $a0, w_ln
	li $v0, 4
	syscall
	lw $ra, -4($fp)
	move $sp, $fp
	lw $fp, -8($fp)
	jr $ra
_writereal:	subu $sp, $sp, 8
	sw $ra, 4($sp)
	sw $fp, 0($sp)
	addu $fp, $sp, 8
	l.d $f0, 0($fp)
	s.d $f0, -16($fp)
	subu $sp, $sp, 8
	l.d $f12, -16($fp)
	li $v0, 3
	syscall
	lw $ra, -4($fp)
	move $sp, $fp
	lw $fp, -8($fp)
	jr $ra
_writerealln:	subu $sp, $sp, 8
	sw $ra, 4($sp)
	sw $fp, 0($sp)
	addu $fp, $sp, 8
	l.d $f0, 0($fp)
	s.d $f0, -16($fp)
	subu $sp, $sp, 8
	l.d $f12, -16($fp)
	li $v0, 3
	syscall
	la $a0, w_ln
	li $v0, 4
	syscall
	lw $ra, -4($fp)
	move $sp, $fp
	lw $fp, -8($fp)
	jr $ra
_writechar:	subu $sp, $sp, 8
	sw $ra, 4($sp)
	sw $fp, 0($sp)
	addu $fp, $sp, 8
	lw $a2, 0($fp)
	sw $a2, -12($fp)
	subu $sp, $sp, 4
	la $a0, w_char
	lw $v1, -12($fp)
	sb $v1, ($a0)
	li $v0, 4
	syscall
	lw $ra, -4($fp)
	move $sp, $fp
	lw $fp, -8($fp)
	jr $ra
_writecharln:	subu $sp, $sp, 8
	sw $ra, 4($sp)
	sw $fp, 0($sp)
	addu $fp, $sp, 8
	lw $a2, 0($fp)
	sw $a2, -12($fp)
	subu $sp, $sp, 4
	la $a0, w_char
	lw $v1, -12($fp)
	sb $v1, ($a0)
	li $v0, 4
	syscall
	la $a0, w_ln
	li $v0, 4
	syscall
	lw $ra, -4($fp)
	move $sp, $fp
	lw $fp, -8($fp)
	jr $ra
_writeboolean:	subu $sp, $sp, 8
	sw $ra, 4($sp)
	sw $fp, 0($sp)
	addu $fp, $sp, 8
	lw $a2, 0($fp)
	sw $a2, -12($fp)
	subu $sp, $sp, 4
	lw $a0, -12($fp)
	beqz $a0, __else1
	la $a0, w_true
	b __ifend1
__else1:	la $a0, w_false
__ifend1:	li $v0, 4
	syscall
	lw $ra, -4($fp)
	move $sp, $fp
	lw $fp, -8($fp)
	jr $ra
_writebooleanln:	subu $sp, $sp, 8
	sw $ra, 4($sp)
	sw $fp, 0($sp)
	addu $fp, $sp, 8
	lw $a2, 0($fp)
	sw $a2, -12($fp)
	subu $sp, $sp, 4
	lw $a0, -12($fp)
	beqz $a0, __else2
	la $a0, w_true
	b __ifend2
__else2:	la $a0, w_false
__ifend2:	li $v0, 4
	syscall
	la $a0, w_ln
	syscall
	lw $ra, -4($fp)
	move $sp, $fp
	lw $fp, -8($fp)
	jr $ra
