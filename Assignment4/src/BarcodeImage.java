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
        
        //find the top row of the code
        int topRow=0;
        for (int x=0;x<str_data.length-1;x++){
        	if ((str_data[x]).trim().equals("")){
        		topRow++;
        	}else{
        		break;
        	}
        }

        //find the bottom row of the code
        int bottomUp=0;
        for (int x=str_data.length-1;x>0;x--){
        	if ((str_data[x]).trim().equals("")){
        		bottomUp++;
        	}else{
        		break;
        	}
        }
        
        int bottomRow=(str_data.length-1)-bottomUp;
        
        //write the code into the image_data array
        //from bottom left corner and up
        for (int x=bottomRow;x>=topRow;x--){
        	String temp=str_data[x].trim();
        	for (int k=0;k<temp.length();k++){
        		if (temp.charAt(k) ==  '*'){
        			setPixel(((MAX_HEIGHT-1)-(bottomRow-x)),k,true);
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
    
    public BarcodeImage clone() throws CloneNotSupportedException{
        BarcodeImage clonedBarcodeImage = new BarcodeImage();
        for (int x=0;x<image_data.length;x++){
            for (int i=0;i<image_data[x].length;i++){
                clonedBarcodeImage.setPixel(x,i,getPixel(x,i));
            }
        }
        
        return clonedBarcodeImage;
    }
    
}