package org.pascal2spim.mips32.instructions;

public enum InstructionType {
    LOAD_IMMEDIATE("li"),
    ADD("add"),
    ADD_FLOATING_POINT("add.d"),
    ADD_UNSIGNED("addu");
//    b
//            bc1t
//    beq
//            beqz
//    c.le.d
//    c.lt.d
//    cvt.d.w
//            div
//    div.d
//            jal
//    jr
//    l.d
//            la
//            lw
//    move
//            mtc1
//    mul
//            rem
//    s.d
//            sb
//    seq
//    sub.d
//            subu
//    sw
//            syscall


    private String code;

    InstructionType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
