.data
	_v1:	.word 0
	_v2:	.word 0
	_v3:	.word 0
	_v4:	.word 0
	_v5:	.word 0
	_v6:	.word 0
	_v7:	.word 0
	_v8:	.word 0
	_v9:	.word 0
	_v10:	.word 0
	_vchar:	.word 0
	w_ln:	.asciiz "\n"
	w_char:	.asciiz "\n"
	w_true:	.asciiz "true"
	w_false:	.asciiz "false"
.text
	.globl _main_
_main_: 	li $t0, '#'
	li $t1, 1
	li $t2, 1
	li $t3, 1
	li $t4, 1
	sw $t0, _vchar
	li $t0, 1
	sw $t0, _v5
	li $t0, 1
	sw $t0, _v6
	li $t0, 1
	sw $t0, _v7
	li $t0, 1
	sw $t0, _v8
	li $t0, 1
	sw $t0, _v9
	li $t0, 1
	sw $t0, _v10
	mul $t0, $t3, $t4
	sw $t1, _v1
	lw $t1, _v5
	div $t0, $t0, $t1
	add $t0, $t2, $t0
	sw $t1, _v5
	lw $t1, _v6
	add $t0, $t0, $t1
	sw $t0, _v1
	lw $t0, _vchar
	beq $t0, 'a', case0_b0
	beq $t0, '#', case0_b1
	b case0_else
case0_b0:	sw $t0, _vchar
	lw $t0, _v7
	sw $t1, _v6
	lw $t1, _v8
	sw $t0, _v7
	add $t0, $t0, $t1
	sw $t1, _v8
	lw $t1, _v9
	add $t0, $t0, $t1
	sw $t1, _v9
	lw $t1, _v10
	add $t0, $t0, $t1
	move $t2, $t0
	sw $t1, _v10
	sw $t2, _v2
	sw $t3, _v3
	sw $t4, _v4
	subu $sp, $sp, 4
	sw $t1, ($sp)
	subu $sp, $sp, 4
	sw $t2, ($sp)
	subu $sp, $sp, 4
	sw $t3, ($sp)
	subu $sp, $sp, 4
	sw $t4, ($sp)
	li $t0, 'a'
	subu $sp, $sp, 4
	sw $t0, ($sp)
	jal _writechar
	addu $sp, $sp, 4
	lw $t4, ($sp)
	addu $sp, $sp, 4
	lw $t3, ($sp)
	addu $sp, $sp, 4
	lw $t2, ($sp)
	addu $sp, $sp, 4
	lw $t1, ($sp)
	addu $sp, $sp, 4
	sw $t1, _v10
	lw $t0, _vchar
	lw $t1, _v6
	b case0_end
case0_b1:	sw $t0, _vchar
	sw $t1, _v6
	sw $t2, _v2
	sw $t3, _v3
	sw $t4, _v4
	subu $sp, $sp, 4
	sw $t0, ($sp)
	subu $sp, $sp, 4
	sw $t1, ($sp)
	subu $sp, $sp, 4
	sw $t2, ($sp)
	subu $sp, $sp, 4
	sw $t3, ($sp)
	subu $sp, $sp, 4
	sw $t4, ($sp)
	li $t0, '#'
	subu $sp, $sp, 4
	sw $t0, ($sp)
	jal _writechar
	addu $sp, $sp, 4
	lw $t4, ($sp)
	addu $sp, $sp, 4
	lw $t3, ($sp)
	addu $sp, $sp, 4
	lw $t2, ($sp)
	addu $sp, $sp, 4
	lw $t1, ($sp)
	addu $sp, $sp, 4
	lw $t0, ($sp)
	addu $sp, $sp, 4
	b case0_end
case0_else:	sw $t0, _vchar
	lw $t0, _v10
	sw $t0, _v10
	add $t0, $t0, $t2
	add $t0, $t0, $t3
	sw $t0, _v10
	sw $t1, _v6
	sw $t2, _v2
	sw $t3, _v3
	sw $t4, _v4
	subu $sp, $sp, 4
	sw $t0, ($sp)
	subu $sp, $sp, 4
	sw $t1, ($sp)
	subu $sp, $sp, 4
	sw $t2, ($sp)
	subu $sp, $sp, 4
	sw $t3, ($sp)
	subu $sp, $sp, 4
	sw $t4, ($sp)
	li $t0, '?'
	subu $sp, $sp, 4
	sw $t0, ($sp)
	jal _writechar
	addu $sp, $sp, 4
	lw $t4, ($sp)
	addu $sp, $sp, 4
	lw $t3, ($sp)
	addu $sp, $sp, 4
	lw $t2, ($sp)
	addu $sp, $sp, 4
	lw $t1, ($sp)
	addu $sp, $sp, 4
	lw $t0, ($sp)
	addu $sp, $sp, 4
	sw $t0, _v10
	lw $t0, _vchar
case0_end:	sw $t0, _vchar
	sw $t1, _v6
	sw $t2, _v2
	sw $t3, _v3
	sw $t4, _v4
	subu $sp, $sp, 4
	sw $t0, ($sp)
	subu $sp, $sp, 4
	sw $t1, ($sp)
	subu $sp, $sp, 4
	sw $t2, ($sp)
	subu $sp, $sp, 4
	sw $t3, ($sp)
	subu $sp, $sp, 4
	sw $t4, ($sp)
	lw $t0, _v2
	subu $sp, $sp, 4
	sw $t0, ($sp)
	jal _writeintln
	addu $sp, $sp, 4
	lw $t4, ($sp)
	addu $sp, $sp, 4
	lw $t3, ($sp)
	addu $sp, $sp, 4
	lw $t2, ($sp)
	addu $sp, $sp, 4
	lw $t1, ($sp)
	addu $sp, $sp, 4
	lw $t0, ($sp)
	addu $sp, $sp, 4
	sw $t0, _vchar
	sw $t1, _v6
	sw $t2, _v2
	sw $t3, _v3
	sw $t4, _v4
	subu $sp, $sp, 4
	sw $t0, ($sp)
	subu $sp, $sp, 4
	sw $t1, ($sp)
	subu $sp, $sp, 4
	sw $t2, ($sp)
	subu $sp, $sp, 4
	sw $t3, ($sp)
	subu $sp, $sp, 4
	sw $t4, ($sp)
	lw $t0, _v10
	subu $sp, $sp, 4
	sw $t0, ($sp)
	jal _writeintln
	addu $sp, $sp, 4
	lw $t4, ($sp)
	addu $sp, $sp, 4
	lw $t3, ($sp)
	addu $sp, $sp, 4
	lw $t2, ($sp)
	addu $sp, $sp, 4
	lw $t1, ($sp)
	addu $sp, $sp, 4
	lw $t0, ($sp)
	addu $sp, $sp, 4
	sw $t0, _vchar
	sw $t1, _v6
	sw $t2, _v2
	sw $t3, _v3
	sw $t4, _v4
	li $v0, 10
	syscall
_writeint:	subu $sp, $sp, 8
	sw $ra, 4($sp)
	sw $fp, 0($sp)
	addu $fp, $sp, 8
	lw $t0, 0($fp)
	sw $t0, -12($fp)
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
	lw $t0, 0($fp)
	sw $t0, -12($fp)
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
	lw $t0, 0($fp)
	sw $t0, -12($fp)
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
	lw $t0, 0($fp)
	sw $t0, -12($fp)
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
	lw $t0, 0($fp)
	sw $t0, -12($fp)
	subu $sp, $sp, 4
	lw $a0, -12($fp)
	beqz $a0, _else1
	la $a0, w_true
	b _ifend1
_else1:	la $a0, w_false
_ifend1:	li $v0, 4
	syscall
	lw $ra, -4($fp)
	move $sp, $fp
	lw $fp, -8($fp)
	jr $ra
_writebooleanln:	subu $sp, $sp, 8
	sw $ra, 4($sp)
	sw $fp, 0($sp)
	addu $fp, $sp, 8
	lw $t0, 0($fp)
	sw $t0, -12($fp)
	subu $sp, $sp, 4
	lw $a0, -12($fp)
	beqz $a0, _else2
	la $a0, w_true
	b _ifend2
_else2:	la $a0, w_false
_ifend2:	li $v0, 4
	syscall
	la $a0, w_ln
	syscall
	lw $ra, -4($fp)
	move $sp, $fp
	lw $fp, -8($fp)
	jr $ra
