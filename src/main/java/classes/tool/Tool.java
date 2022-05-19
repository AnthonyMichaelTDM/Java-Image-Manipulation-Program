package classes.tool;

public class Tool {
    //data
    public enum ToolType {
        COLOR_MODIFICATION,
        FILTER;
    }
    private ToolTitle tool;
    public enum ToolTitle {
        //States
        REMOVE_COLOR (ToolType.COLOR_MODIFICATION),
        TRIM_COLOR (ToolType.COLOR_MODIFICATION),
        NEGATE_COLOR (ToolType.COLOR_MODIFICATION),
        REPLACE_COLOR (ToolType.COLOR_MODIFICATION),
        GRAYSCALE (ToolType.COLOR_MODIFICATION),

        EDGE_DETECTION (ToolType.FILTER),
        BRIGHTEN (ToolType.FILTER),
        DARKEN (ToolType.FILTER),
        SIMPLIFY_COLOR (ToolType.FILTER),
        KMEANS_SIMPLIFY (ToolType.FILTER),
        ;

        //DATA
        private ToolType type;

        //Constructor
        ToolTitle (ToolType type) {
            this.type = type;
        }

        //methods
        public ToolType getType() {return this.type;}
    }

    //methods
    /**
     * sets tool to given tool
     * @param tool ToolTitle to set tool to
     */
    public void setTool(ToolTitle tool) {
        this.tool = tool;
    }
    /**
     * @return ToolTitle of tool being represented
     */
    public ToolTitle getTool() {
        return this.tool;
    }
    /**
     * @return ToolType of tool being represented 
     */
    public ToolType getType() {
        return this.tool.getType();
    } 
}
