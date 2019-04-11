/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maturita;

import java.sql.Date;
import java.text.ParseException;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author skolniPC
 */
public class Trade {
    
   
    
    private final SimpleStringProperty currency,type;
    private final SimpleIntegerProperty isActive,accID;
    private final SimpleFloatProperty enterPrice,positionSize,sl,result;
    private int ID;
    private Date date;
    private Float originalResult;
    
    public Trade(int ID,String currency, String type, float positionSize, float sl, float enterPrice, int isActive, int accID, float result,Date date) throws ParseException {
        this.currency = new SimpleStringProperty(currency);
        this.type  = new SimpleStringProperty(type);
        this.positionSize = new SimpleFloatProperty(positionSize);
        this.sl =  new SimpleFloatProperty(sl);
        this.enterPrice = new SimpleFloatProperty(enterPrice);
        this.isActive =  new SimpleIntegerProperty(isActive);
        this.accID = new SimpleIntegerProperty(accID);
        this.result = new SimpleFloatProperty(result);
        this.ID = ID;
        this.date = date;
        
        
        
        }

    public String getCurrency() {
        return currency.get();
    }

    public String getType() {
        return type.get();
    }

    public float getPositionSize() {
        return positionSize.get();
    }

    public float getSl() {
        return sl.get();
    }

    public float getEnterPrice() {
        return enterPrice.get();
    }

    public int getIsActive() {
        return isActive.get();
    }

    public int getAccID() {
        return accID.get();
    }
  
    /**
     * mění parametr tradu  v db is Active 0 - trade je dokončen 1-trade je otevřený
     *
     * @param isActive
     */
    public void setIsActive(int isActive)
    {
        this.isActive.set(isActive);
    }
 
    public float getResult()
    {
        return this.result.get();
    }
    
    public void setCurrency(String currency)
    {
        this.currency.set(currency);
    }
    
    public void setType(String type)
    {
        this.type.set(type);
    }
    
    public void setPositionSize(float positionSize)
    {
        this.positionSize.set(positionSize);
    }
    
    public void setSl(float sl)
    {
        this.sl.set(sl);
    }
    
    public void setEnterPrice(float enterPrice)
    {
        this.enterPrice.set(enterPrice);
    }
        
    public int getID()
    {
        return this.ID;
    }
    

   public  String getDate() {
        return this.date.toString();
    }

    public void setResult(Float value) {
        this.result.set(value);
    }

    public void setOriginalResult(Float valueOf) {
        this.originalResult = valueOf;
    }
    public float getOriginalResult()
    {
        return this.originalResult;
    }
}
