public class Program {
    private String name;
    private Block block;

    public Program(String name, Block block) {
        this.name = name;
        this.block = block;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public void generateCode() {
        SymbolTable st = SymbolTable.getInstance();
        st.generateCode();
        block.generateCode();
    }
}