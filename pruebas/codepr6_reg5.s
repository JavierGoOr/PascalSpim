.data
	_v1:	.double 0.0
	_v2:	.double 0.0
	_v3:	.double 0.0
	_v4:	.double 0.0
	_v5:	.double 0.0
	_v6:	.double 0.0
	_v7:	.double 0.0
	_v8:	.double 0.0
	_v9:	.double 0.0
	_v10:	.double 0.0
	_vchar:	.word 0
	w_ln:	.asciiz "\n"
	w_char:	.asciiz "\n"
	w_true:	.asciiz "true"
	w_false:	.asciiz "false"
.text
	.globl _main_
_main_: 	li $a2, '#'
	li $a3, 1
	mtc1 $a3, $f0
	cvt.d.w $f0, $f0
	li $a3, 1
	mtc1 $a3, $f2
	cvt.d.w $f2, $f2
	li $a3, 1
	mtc1 $a3, $f4
	cvt.d.w $f4, $f4
	li $a3, 1
	mtc1 $a3, $f6
	cvt.d.w $f6, $f6
	li $a3, 1
	mtc1 $a3, $f8
	cvt.d.w $f8, $f8
	li $a3, 1
	s.d $f0, _v1
	mtc1 $a3, $f0
	cvt.d.w $f0, $f0
	li $a3, 1
	s.d $f0, _v6
	mtc1 $a3, $f0
	cvt.d.w $f0, $f0
	li $a3, 1
	s.d $f0, _v7
	mtc1 $a3, $f0
	cvt.d.w $f0, $f0
	li $a3, 1
	s.d $f0, _v8
	mtc1 $a3, $f0
	cvt.d.w $f0, $f0
	li $a3, 1
	s.d $f0, _v9
	mtc1 $a3, $f0
	cvt.d.w $f0, $f0
	s.d $f0, _v10
	mul.d $f0, $f4, $f6
	div.d $f0, $f0, $f8
	add.d $f0, $f2, $f0
	s.d $f2, _v2
	l.d $f2, _v6
	add.d $f0, $f0, $f2
	beq $a2, 'a', case0_b0
	beq $a2, '#', case0_b1
	b case0_else
case0_b0:	s.d $f0, _v1
	l.d $f0, _v7
	s.d $f2, _v6
	l.d $f2, _v8
	s.d $f0, _v7
	add.d $f0, $f0, $f2
	s.d $f2, _v8
	l.d $f2, _v9
	add.d $f0, $f0, $f2
	s.d $f2, _v9
	l.d $f2, _v10
	add.d $f0, $f0, $f2
	sw $a2, _vchar
	s.d $f0, _v2
	s.d $f2, _v10
	s.d $f4, _v3
	s.d $f6, _v4
	s.d $f8, _v5
	subu $sp, $sp, 4
	sw $a2, ($sp)
	subu $sp, $sp, 8
	s.d $f0, ($sp)
	subu $sp, $sp, 8
	s.d $f2, ($sp)
	subu $sp, $sp, 8
	s.d $f4, ($sp)
	subu $sp, $sp, 8
	s.d $f6, ($sp)
	subu $sp, $sp, 8
	s.d $f8, ($sp)
	li $a2, 'a'
	subu $sp, $sp, 4
	sw $a2, ($sp)
	jal _writechar
	addu $sp, $sp, 4
	l.d $f8, ($sp)
	addu $sp, $sp, 8
	l.d $f6, ($sp)
	addu $sp, $sp, 8
	l.d $f4, ($sp)
	addu $sp, $sp, 8
	l.d $f2, ($sp)
	addu $sp, $sp, 8
	l.d $f0, ($sp)
	addu $sp, $sp, 8
	lw $a2, ($sp)
	addu $sp, $sp, 4
	s.d $f0, _v2
	s.d $f2, _v10
	l.d $f0, _v1
	l.d $f2, _v6
	b case0_end
case0_b1:	sw $a2, _vchar
	s.d $f0, _v1
	s.d $f2, _v6
	s.d $f4, _v3
	s.d $f6, _v4
	s.d $f8, _v5
	subu $sp, $sp, 4
	sw $a2, ($sp)
	subu $sp, $sp, 8
	s.d $f0, ($sp)
	subu $sp, $sp, 8
	s.d $f2, ($sp)
	subu $sp, $sp, 8
	s.d $f4, ($sp)
	subu $sp, $sp, 8
	s.d $f6, ($sp)
	subu $sp, $sp, 8
	s.d $f8, ($sp)
	li $a2, '#'
	subu $sp, $sp, 4
	sw $a2, ($sp)
	jal _writechar
	addu $sp, $sp, 4
	l.d $f8, ($sp)
	addu $sp, $sp, 8
	l.d $f6, ($sp)
	addu $sp, $sp, 8
	l.d $f4, ($sp)
	addu $sp, $sp, 8
	l.d $f2, ($sp)
	addu $sp, $sp, 8
	l.d $f0, ($sp)
	addu $sp, $sp, 8
	lw $a2, ($sp)
	addu $sp, $sp, 4
	b case0_end
case0_else:	s.d $f0, _v1
	l.d $f0, _v10
	s.d $f2, _v6
	l.d $f2, _v2
	s.d $f0, _v10
	add.d $f0, $f0, $f2
	add.d $f0, $f0, $f4
	sw $a2, _vchar
	s.d $f0, _v10
	s.d $f2, _v2
	s.d $f4, _v3
	s.d $f6, _v4
	s.d $f8, _v5
	subu $sp, $sp, 4
	sw $a2, ($sp)
	subu $sp, $sp, 8
	s.d $f0, ($sp)
	subu $sp, $sp, 8
	s.d $f2, ($sp)
	subu $sp, $sp, 8
	s.d $f4, ($sp)
	subu $sp, $sp, 8
	s.d $f6, ($sp)
	subu $sp, $sp, 8
	s.d $f8, ($sp)
	li $a2, '?'
	subu $sp, $sp, 4
	sw $a2, ($sp)
	jal _writechar
	addu $sp, $sp, 4
	l.d $f8, ($sp)
	addu $sp, $sp, 8
	l.d $f6, ($sp)
	addu $sp, $sp, 8
	l.d $f4, ($sp)
	addu $sp, $sp, 8
	l.d $f2, ($sp)
	addu $sp, $sp, 8
	l.d $f0, ($sp)
	addu $sp, $sp, 8
	lw $a2, ($sp)
	addu $sp, $sp, 4
	s.d $f0, _v10
	s.d $f2, _v2
	l.d $f0, _v1
	l.d $f2, _v6
case0_end:	sw $a2, _vchar
	s.d $f0, _v1
	s.d $f2, _v6
	s.d $f4, _v3
	s.d $f6, _v4
	s.d $f8, _v5
	subu $sp, $sp, 4
	sw $a2, ($sp)
	subu $sp, $sp, 8
	s.d $f0, ($sp)
	subu $sp, $sp, 8
	s.d $f2, ($sp)
	subu $sp, $sp, 8
	s.d $f4, ($sp)
	subu $sp, $sp, 8
	s.d $f6, ($sp)
	subu $sp, $sp, 8
	s.d $f8, ($sp)
	l.d $f0, _v2
	subu $sp, $sp, 8
	s.d $f0, ($sp)
	jal _writerealln
	addu $sp, $sp, 8
	l.d $f8, ($sp)
	addu $sp, $sp, 8
	l.d $f6, ($sp)
	addu $sp, $sp, 8
	l.d $f4, ($sp)
	addu $sp, $sp, 8
	l.d $f2, ($sp)
	addu $sp, $sp, 8
	l.d $f0, ($sp)
	addu $sp, $sp, 8
	lw $a2, ($sp)
	addu $sp, $sp, 4
	sw $a2, _vchar
	s.d $f0, _v1
	s.d $f2, _v6
	s.d $f4, _v3
	s.d $f6, _v4
	s.d $f8, _v5
	subu $sp, $sp, 4
	sw $a2, ($sp)
	subu $sp, $sp, 8
	s.d $f0, ($sp)
	subu $sp, $sp, 8
	s.d $f2, ($sp)
	subu $sp, $sp, 8
	s.d $f4, ($sp)
	subu $sp, $sp, 8
	s.d $f6, ($sp)
	subu $sp, $sp, 8
	s.d $f8, ($sp)
	l.d $f0, _v10
	subu $sp, $sp, 8
	s.d $f0, ($sp)
	jal _writerealln
	addu $sp, $sp, 8
	l.d $f8, ($sp)
	addu $sp, $sp, 8
	l.d $f6, ($sp)
	addu $sp, $sp, 8
	l.d $f4, ($sp)
	addu $sp, $sp, 8
	l.d $f2, ($sp)
	addu $sp, $sp, 8
	l.d $f0, ($sp)
	addu $sp, $sp, 8
	lw $a2, ($sp)
	addu $sp, $sp, 4
	sw $a2, _vchar
	s.d $f0, _v1
	s.d $f2, _v6
	s.d $f4, _v3
	s.d $f6, _v4
	s.d $f8, _v5
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
	lw $a2, 0($fp)
	sw $a2, -12($fp)
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
