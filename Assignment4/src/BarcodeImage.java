class BarcodeImage implements Cloneable {
    
    public static final int MAX_HEIGHT = 30;
    public static final int MAX_WIDTH = 65;
    public static final int MAX_LENGTH = MAX_HEIGHT*MAX_WIDTH;
    
    private boolean[][] image_data;
    
    public BarcodeImage(){
        //initialize 2d array - all elements default to false
        image_data = new boolean[MAX_HEIGHT][MAX_WIDTH];
    }
    
    public BarcodeImage(String[] str_data){
        //initialize 2d array - all elements default to false
        image_data = new boolean[MAX_HEIGHT][MAX_WIDTH];
        
        if (checkSize(str_data)){
	        //convert 1d string array to 2d boolean array
	        for (int x=0;x<str_data.length;x++){
	            if (str_data[x].equals("*")){
	                setPixel((x/MAX_WIDTH),(x%MAX_WIDTH),true);
	            }
	        }
        }
    }
    
    public boolean getPixel(int row, int col){
        
        if (isValid(row, col)){
            return image_data[row][col];
        }
        return false;
        
    }
    
    public boolean setPixel(int row, int col, boolean value){
        if (isValid(row, col)){
            image_data[row][col]=value;
            return true;
        }
        return false;
    }
    
    private boolean isValid(int row, int col){
        if ((row>=0 && row<MAX_HEIGHT) && (col>=0 && col<MAX_WIDTH)){
            return true;
        }
        return false;
    }
    
    private boolean checkSize(String[] data){
    	if (data.length<=MAX_LENGTH && data.length>=0){
    		return true;
    	}
        return false;
    }
    
    public void displayToConsole(){
        for (int x=0;x<image_data.length;x++){
            for (int i=0;i<image_data[x].length;i++){
                if (this.getPixel(x,i)){
                    System.out.print("*");
                }else{
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
    
    public BarcodeImage clone(){
        BarcodeImage clonedBarcodeImage = new BarcodeImage();
        for (int x=0;x<image_data.length;x++){
            for (int i=0;i<image_data[x].length;i++){
                clonedBarcodeImage.setPixel(x,i,getPixel(x,i));
            }
        }
        
        return clonedBarcodeImage;
    }
}