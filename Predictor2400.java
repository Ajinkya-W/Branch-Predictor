
public class Predictor2400 extends Predictor {
	Table lht=new Table(256,7);
	Table lpt=new Table(128,2);
	public Predictor2400() {
		for(int row_num=0;row_num<lht.getNumEntries();row_num++)
			lht.setInteger(row_num,0,lht.getBitsPerEntry()-1,5);
		for(int row_num=0;row_num<lpt.getNumEntries();row_num++)
			lpt.setInteger(row_num,0,1,1);
	}


	public void Train(long address, boolean outcome, boolean predict) {
      int indexOflht=(int)(address%lht.getNumEntries());
      int indexOflpt=lht.getInteger(indexOflht,0,lht.getBitsPerEntry()-1);
      int valToBeChanged=lpt.getInteger(indexOflpt,0,1);
      int leftShift=1<<(lht.getBitsPerEntry()-1);
      int rightShift=lht.getInteger(indexOflht,0,lht.getBitsPerEntry()-1)>>1;
      int sum=rightShift+leftShift;
          predict=predict(address);
      //saturation counter    
      if(predict==false) {
    	  if(outcome==true) {
    		  lpt.setInteger(indexOflpt,0,1,valToBeChanged+1);
    		  lht.setInteger(indexOflht,0,lht.getBitsPerEntry()-1,sum);
    	  }
    	  else {
    		  if(lpt.getBit(indexOflpt,1)==true) {
    			  lpt.setInteger(indexOflpt,0,1,valToBeChanged-1);
    			  lht.setInteger(indexOflht,0,lht.getBitsPerEntry()-1,rightShift);
    		  }
		  else
			lht.setInteger(indexOflht,0,lht.getBitsPerEntry()-1,rightShift);
    	  }	  
      }
      else {
    	   if (outcome==true) {
    		  if(lpt.getBit(indexOflpt,1)==false) {
    			  lpt.setInteger(indexOflpt,0,1,valToBeChanged+1);
    			  lht.setInteger(indexOflht,0,lht.getBitsPerEntry()-1,sum);
    		  }
		  else
			lht.setInteger(indexOflht,0,lht.getBitsPerEntry()-1,sum);
    	   }
    	   else {
    		   lpt.setInteger(indexOflpt,0,1,valToBeChanged-1);
    		   lht.setInteger(indexOflht,0,lht.getBitsPerEntry()-1,rightShift);
    	   }
      }
	}


	public boolean predict(long address){
		if(lpt.getBit(lht.getInteger((int)(address%lht.getNumEntries()),0,lht.getBitsPerEntry()-1),0)==true)
			return false;
		else
			return true;
	}

}
