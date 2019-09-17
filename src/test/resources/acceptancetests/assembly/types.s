.data
	_vglobal:	.word 0
	_aglobal:	.space 432
	_vreal:	.double 0.0
	real_ct0:	.double 3.14
	w_ln:	.asciiz "\n"
	w_char:	.asciiz "\n"
	w_true:	.asciiz "true"
	w_false:	.asciiz "false"
.text
	.globl _main_
_main_: 	li $a2, 1
	li $a3, 4
	mtc1 $a3, $f0
	cvt.d.w $f0, $f0
	l.d $f2, real_ct0
	li $a3, 1
	move $a3, $a3
	mul $a3, $a3, 3
	li $t0, 1
	mul $t0, $t0, -1
	add $a3, $a3, $t0
	sub $a3, $a3, -2
	mul $a3, $a3, 3
	li $t0, 2
	add $a3, $a3, $t0
	sub $a3, $a3, -2
	mul $a3, $a3, 8
	la $t0, _aglobal
	add $a3, $a3, $t0
	s.d $f2, ($a3)
	sw $a2, _vglobal
	s.d $f0, _vreal
	subu $sp, $sp, 4
	sw $a2, ($sp)
	subu $sp, $sp, 4
	sw $a3, ($sp)
	subu $sp, $sp, 8
	s.d $f0, ($sp)
	li $a2, 1
	li $a3, 1
	sub $a2, $a2, $a3
	subu $sp, $sp, 4
	sw $a2, ($sp)
	li $a2, 2
	li $a3, 2
	add $a2, $a2, $a3
	subu $sp, $sp, 4
	sw $a2, ($sp)
	l.d $f0, _vreal
	subu $sp, $sp, 8
	s.d $f0, ($sp)
	li $a2, -1
	mtc1 $a2, $f2
	cvt.d.w $f2, $f2
	mul.d $f0, $f0, $f2
	li $a2, 1
	mtc1 $a2, $f2
	cvt.d.w $f2, $f2
	add.d $f2, $f0, $f2
	li $a2, 2
	mtc1 $a2, $f4
	cvt.d.w $f4, $f4
	add.d $f2, $f2, $f4
	subu $sp, $sp, 8
	s.d $f2, ($sp)
	jal _f1
	addu $sp, $sp, 24
	l.d $f0, ($sp)
	addu $sp, $sp, 8
	lw $a3, ($sp)
	addu $sp, $sp, 4
	lw $a2, ($sp)
	addu $sp, $sp, 4
	lw $a2, _vglobal
	l.d $f0, _vreal
	mov.d $f2, $f30
	mov.d $f0, $f2
	sw $a2, _vglobal
	s.d $f0, _vreal
	subu $sp, $sp, 4
	sw $a2, ($sp)
	subu $sp, $sp, 4
	sw $a3, ($sp)
	subu $sp, $sp, 8
	s.d $f0, ($sp)
	li $a2, 0
	move $a2, $a2
	mul $a2, $a2, 3
	li $a3, 0
	add $a2, $a2, $a3
	sub $a2, $a2, -2
	mul $a2, $a2, 3
	li $a3, 0
	add $a2, $a2, $a3
	sub $a2, $a2, -2
	mul $a2, $a2, 8
	la $a3, _aglobal
	add $a2, $a2, $a3
	l.d $f0, ($a2)
	subu $sp, $sp, 8
	s.d $f0, ($sp)
	jal _writerealln
	addu $sp, $sp, 8
	l.d $f0, ($sp)
	addu $sp, $sp, 8
	lw $a3, ($sp)
	addu $sp, $sp, 4
	lw $a2, ($sp)
	addu $sp, $sp, 4
	lw $a2, _vglobal
	l.d $f0, _vreal
	sw $a2, _vglobal
	s.d $f0, _vreal
	subu $sp, $sp, 4
	sw $a2, ($sp)
	subu $sp, $sp, 4
	sw $a3, ($sp)
	subu $sp, $sp, 8
	s.d $f0, ($sp)
	li $a2, 1
	move $a2, $a2
	mul $a2, $a2, 3
	li $a3, 1
	mul $a3, $a3, -1
	add $a2, $a2, $a3
	sub $a2, $a2, -2
	mul $a2, $a2, 3
	li $a3, 2
	add $a2, $a2, $a3
	sub $a2, $a2, -2
	mul $a2, $a2, 8
	la $a3, _aglobal
	add $a2, $a2, $a3
	l.d $f0, ($a2)
	subu $sp, $sp, 8
	s.d $f0, ($sp)
	jal _writerealln
	addu $sp, $sp, 8
	l.d $f0, ($sp)
	addu $sp, $sp, 8
	lw $a3, ($sp)
	addu $sp, $sp, 4
	lw $a2, ($sp)
	addu $sp, $sp, 4
	lw $a2, _vglobal
	l.d $f0, _vreal
	sw $a2, _vglobal
	s.d $f0, _vreal
	subu $sp, $sp, 4
	sw $a2, ($sp)
	subu $sp, $sp, 4
	sw $a3, ($sp)
	subu $sp, $sp, 8
	s.d $f0, ($sp)
	l.d $f0, _vreal
	subu $sp, $sp, 8
	s.d $f0, ($sp)
	jal _writerealln
	addu $sp, $sp, 8
	l.d $f0, ($sp)
	addu $sp, $sp, 8
	lw $a3, ($sp)
	addu $sp, $sp, 4
	lw $a2, ($sp)
	addu $sp, $sp, 4
	lw $a2, _vglobal
	l.d $f0, _vreal
	sw $a2, _vglobal
	s.d $f0, _vreal
	subu $sp, $sp, 4
	sw $a2, ($sp)
	subu $sp, $sp, 4
	sw $a3, ($sp)
	subu $sp, $sp, 8
	s.d $f0, ($sp)
	lw $a2, _vglobal
	subu $sp, $sp, 4
	sw $a2, ($sp)
	jal _writeboolean
	addu $sp, $sp, 4
	l.d $f0, ($sp)
	addu $sp, $sp, 8
	lw $a3, ($sp)
	addu $sp, $sp, 4
	lw $a2, ($sp)
	addu $sp, $sp, 4
	lw $a2, _vglobal
	l.d $f0, _vreal
	sw $a2, _vglobal
	s.d $f0, _vreal
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
	beqz $a0, __else0
	la $a0, w_true
	b __ifend0
__else0:	la $a0, w_false
__ifend0:	li $v0, 4
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
	beqz $a0, __else1
	la $a0, w_true
	b __ifend1
__else1:	la $a0, w_false
__ifend1:	li $v0, 4
	syscall
	la $a0, w_ln
	syscall
	lw $ra, -4($fp)
	move $sp, $fp
	lw $fp, -8($fp)
	jr $ra
_p1:	subu $sp, $sp, 8
	sw $ra, 4($sp)
	sw $fp, 0($sp)
	addu $fp, $sp, 8
	subu $sp, $sp, 0
	li $a2, '@'
	subu $sp, $sp, 4
	sw $a2, ($sp)
	jal _writecharln
	addu $sp, $sp, 4
	lw $ra, -4($fp)
	move $sp, $fp
	lw $fp, -8($fp)
	jr $ra
_f1:	subu $sp, $sp, 8
	sw $ra, 4($sp)
	sw $fp, 0($sp)
	addu $fp, $sp, 8
	lw $a2, 20($fp)
	sw $a2, -12($fp)
	lw $a2, 16($fp)
	sw $a2, -16($fp)
	l.d $f0, 8($fp)
	s.d $f0, -24($fp)
	l.d $f0, 0($fp)
	s.d $f0, -32($fp)
	subu $sp, $sp, 36
	li $a2, 0
	sw $a2, _vglobal
	subu $sp, $sp, 4
	sw $a2, ($sp)
	jal _p1
	addu $sp, $sp, 0
	lw $a2, ($sp)
	addu $sp, $sp, 4
	lw $a2, _vglobal
	li $a3, 1
	beqz $a3, __ifend2
	li $a3, 0
__while3:	li $t0, 3
	slt $t0, $a3, $t0
	beqz $t0, __whileEnd3
	li $t0, 2
	mul $t0, $t0, -1
__while4:	li $t1, 1
	slt $t1, $t0, $t1
	beqz $t1, __whileEnd4
	li $t1, 2
	mul $t1, $t1, -1
__while5:	li $t2, 4
	slt $t2, $t1, $t2
	beqz $t2, __whileEnd5
	li $t2, 1
	sne $t2, $a3, $t2
	li $t3, 1
	mul $t3, $t3, -1
	sne $t3, $t0, $t3
	and $t2, $t2, $t3
	li $t3, 2
	sne $t3, $t1, $t3
	and $t2, $t2, $t3
	beqz $t2, __ifend6
	li $t2, 1
	mul $t2, $t2, -1
	move $t3, $a3
	mul $t3, $t3, 3
	add $t3, $t3, $t0
	sub $t3, $t3, -2
	mul $t3, $t3, 3
	add $t3, $t3, $t1
	sub $t3, $t3, -2
	mul $t3, $t3, 8
	la $t4, _aglobal
	add $t3, $t3, $t4
	mtc1 $t2, $f0
	cvt.d.w $f0, $f0
	s.d $f0, ($t3)
__ifend6:	li $t2, 1
	add $t2, $t1, $t2
	move $t1, $t2
	b __while5
__whileEnd5:	li $t2, 1
	add $t2, $t0, $t2
	move $t0, $t2
	sw $t1, -44($fp)
	b __while4
__whileEnd4:	li $t1, 1
	add $t1, $a3, $t1
	move $a3, $t1
	sw $t0, -40($fp)
	b __while3
__whileEnd3:	sw $a3, -36($fp)
__ifend2:	li $a3, 1
	move $a3, $a3
	mul $a3, $a3, 3
	li $t0, 1
	mul $t0, $t0, -1
	add $a3, $a3, $t0
	sub $a3, $a3, -2
	mul $a3, $a3, 3
	li $t0, 2
	add $a3, $a3, $t0
	sub $a3, $a3, -2
	mul $a3, $a3, 8
	la $t0, _aglobal
	add $a3, $a3, $t0
	l.d $f0, ($a3)
	mov.d $f30, $f0
	sw $a2, _vglobal
	lw $ra, -4($fp)
	move $sp, $fp
	lw $fp, -8($fp)
	jr $ra
