/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maturita;

/**
 *
 * @author skolniPC
 */
public class Account {
    private float balance,isActive,Winrate,Avg_win;
    private float RRR;
    private String accountNumber;

    public Account(String accountNumber, float balance, float isActive, float Winrate, float Avg_win, float RRR) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.isActive = isActive;
        this.Winrate = Winrate;
        this.Avg_win = Avg_win;
        this.RRR = RRR;
    }

    @Override
    public String toString() {
        return "Account n."  + this.accountNumber + "Balance : " + this.balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public float getBalance() {
        return balance;
    }

    public float getIsActive() {
        return isActive;
    }

    public float getWinrate() {
        return Winrate;
    }

    public float getAvg_win() {
        return Avg_win;
    }

    public float getRRR() {
        return RRR;
    }
    
   

 
    
    
    
    
}