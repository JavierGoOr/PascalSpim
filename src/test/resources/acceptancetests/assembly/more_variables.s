.data
	_i:	.word 0
	_j:	.word 0
	_vchar:	.word 0
	real_ct0:	.double -3.2
	real_ct1:	.double -3.2
	real_ct2:	.double -3.2
	real_ct3:	.double -3.2
	w_ln:	.asciiz "\n"
	w_char:	.asciiz "\n"
	w_true:	.asciiz "true"
	w_false:	.asciiz "false"
.text
	.globl _main_
_main_: 	li $a2, -3
	mul $a2, $a2, -1
	l.d $f0, real_ct0
	mtc1 $a2, $f2
	cvt.d.w $f2, $f2
	add.d $f0, $f2, $f0
	li $a2, -3
	li $a3, -3
	mtc1 $a2, $f2
	cvt.d.w $f2, $f2
	mtc1 $a3, $f4
	cvt.d.w $f4, $f4
	div.d $f2, $f2, $f4
	sub.d $f0, $f0, $f2
	li $a2, -3
	li $a3, -3
	div $a2, $a2, $a3
	li $a3, -3
	mul $a3, $a3, -1
	mul $a2, $a2, $a3
	li $a3, 4
	rem $a2, $a2, $a3
	mtc1 $a2, $f2
	cvt.d.w $f2, $f2
	add.d $f0, $f0, $f2
	subu $sp, $sp, 8
	s.d $f0, ($sp)
	jal _writereal
	addu $sp, $sp, 8
	li $a2, -3
	mul $a2, $a2, -1
	l.d $f0, real_ct1
	mtc1 $a2, $f2
	cvt.d.w $f2, $f2
	add.d $f0, $f2, $f0
	li $a2, -3
	li $a3, -3
	mtc1 $a2, $f2
	cvt.d.w $f2, $f2
	mtc1 $a3, $f4
	cvt.d.w $f4, $f4
	div.d $f2, $f2, $f4
	sub.d $f0, $f0, $f2
	li $a2, -3
	li $a3, -3
	div $a2, $a2, $a3
	li $a3, -3
	mul $a3, $a3, -1
	mul $a2, $a2, $a3
	li $a3, 4
	rem $a2, $a2, $a3
	mtc1 $a2, $f2
	cvt.d.w $f2, $f2
	add.d $f0, $f0, $f2
	l.d $f2, real_ct2
	li $a2, -3
	mtc1 $a2, $f4
	cvt.d.w $f4, $f4
	div.d $f2, $f2, $f4
	c.lt.d $f0, $f2
	bc1t flt_else0
	li $a2, 1
	b flt_end0
flt_else0:	li $a2, 0
flt_end0:	li $a3, 1
	seq $a2, $a2, $a3
	subu $sp, $sp, 4
	sw $a2, ($sp)
	jal _writebooleanln
	addu $sp, $sp, 4
	li $a2, 0
__while1:	li $a3, -3
	mul $a3, $a3, -1
	l.d $f0, real_ct3
	mtc1 $a3, $f2
	cvt.d.w $f2, $f2
	add.d $f0, $f2, $f0
	li $a3, -3
	li $t0, -3
	mtc1 $a3, $f2
	cvt.d.w $f2, $f2
	mtc1 $t0, $f4
	cvt.d.w $f4, $f4
	div.d $f2, $f2, $f4
	sub.d $f0, $f0, $f2
	li $a3, -3
	li $t0, -3
	div $a3, $a3, $t0
	li $t0, -3
	mul $t0, $t0, -1
	mul $a3, $a3, $t0
	li $t0, 4
	rem $a3, $a3, $t0
	mtc1 $a3, $f2
	cvt.d.w $f2, $f2
	add.d $f0, $f0, $f2
	mtc1 $a2, $f2
	cvt.d.w $f2, $f2
	sub.d $f0, $f0, $f2
	li $a3, 0
	mtc1 $a3, $f2
	cvt.d.w $f2, $f2
	c.le.d $f0, $f2
	bc1t flt_else2
	li $a3, 1
	b flt_end2
flt_else2:	li $a3, 0
flt_end2:	beqz $a3, __whileEnd1
	li $a3, 1
	add $a3, $a2, $a3
	move $a2, $a3
	b __while1
__whileEnd1:	sw $a2, _i
	subu $sp, $sp, 4
	sw $a2, ($sp)
	lw $a2, _i
	subu $sp, $sp, 4
	sw $a2, ($sp)
	jal _writeintln
	addu $sp, $sp, 4
	lw $a2, ($sp)
	addu $sp, $sp, 4
	lw $a2, _i
	li $a3, '#'
	beq $a3, 'a', case3_b0
	beq $a3, '#', case3_b1
	b case3_else
case3_b0:	sw $a2, _i
	sw $a3, _vchar
	subu $sp, $sp, 4
	sw $a2, ($sp)
	subu $sp, $sp, 4
	sw $a3, ($sp)
	li $a2, 'a'
	subu $sp, $sp, 4
	sw $a2, ($sp)
	jal _writechar
	addu $sp, $sp, 4
	lw $a3, ($sp)
	addu $sp, $sp, 4
	lw $a2, ($sp)
	addu $sp, $sp, 4
	lw $a2, _i
	lw $a3, _vchar
	b case3_end
case3_b1:	sw $a2, _i
	sw $a3, _vchar
	subu $sp, $sp, 4
	sw $a2, ($sp)
	subu $sp, $sp, 4
	sw $a3, ($sp)
	li $a2, '#'
	subu $sp, $sp, 4
	sw $a2, ($sp)
	jal _writechar
	addu $sp, $sp, 4
	lw $a3, ($sp)
	addu $sp, $sp, 4
	lw $a2, ($sp)
	addu $sp, $sp, 4
	lw $a2, _i
	lw $a3, _vchar
	b case3_end
case3_else:	sw $a2, _i
	sw $a3, _vchar
	subu $sp, $sp, 4
	sw $a2, ($sp)
	subu $sp, $sp, 4
	sw $a3, ($sp)
	li $a2, '?'
	subu $sp, $sp, 4
	sw $a2, ($sp)
	jal _writechar
	addu $sp, $sp, 4
	lw $a3, ($sp)
	addu $sp, $sp, 4
	lw $a2, ($sp)
	addu $sp, $sp, 4
	lw $a2, _i
	lw $a3, _vchar
case3_end:	sw $a2, _i
	sw $a3, _vchar
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
	beqz $a0, __else4
	la $a0, w_true
	b __ifend4
__else4:	la $a0, w_false
__ifend4:	li $v0, 4
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
	beqz $a0, __else5
	la $a0, w_true
	b __ifend5
__else5:	la $a0, w_false
__ifend5:	li $v0, 4
	syscall
	la $a0, w_ln
	syscall
	lw $ra, -4($fp)
	move $sp, $fp
	lw $fp, -8($fp)
	jr $ra