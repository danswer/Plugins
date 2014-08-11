package Recipes;


import com.fidessa.inf.oa.OpenAccess;
import com.fidessa.inf.utl.StructuredSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author daniel.jones
 */
public class Instruction {
    private int stepNumber;
    private String command;
    private StructuredSet sendMessage;
    private StructuredSet resultMessage;
    
    
    public Instruction() {
        stepNumber = 0;
        command = "";
    }
    
}
