package com.crio.codingame.commands;

import java.util.List;
import javax.management.openmbean.InvalidOpenTypeException;
import com.crio.codingame.dtos.UserRegistrationDto;
import com.crio.codingame.exceptions.ContestNotFoundException;
import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.codingame.services.IUserService;

public class AttendContestCommand implements ICommand{

    private final IUserService userService;
    
    public AttendContestCommand(IUserService userService) {
        this.userService = userService;
    }

    // TODO: CRIO_TASK_MODULE_CONTROLLER
    // Execute attendContest method of IUserService and print the result.
    // Also Handle Exceptions and print the error messsages if any.
    // Look for the unit tests to see the expected output.
    // Sample Input Token List:- ["ATTEND_CONTEST","3","Joey"]
    // Hint - Use Parameterized Exceptions in the Service class to match with the Unit Tests Output.
    //attendContest(String contestId, String userName) throws ContestNotFoundException, UserNotFoundException, InvalidOperationException;

    @Override
    public void execute(List<String> tokens) {
        String contestID=tokens.get(1);
        String username=tokens.get(2);
        
        try{
            UserRegistrationDto registeredUser=userService.attendContest(contestID, username);
            System.out.println(registeredUser);
        }
        catch(ContestNotFoundException e){
            System.out.println("Cannot Attend Contest. Contest for given id:"+contestID+" not found!");
        }
        catch(UserNotFoundException e){
            System.out.println("Cannot Attend Contest. User for given name:"+ username+" not found!");
        }
        catch(InvalidOpenTypeException e){
            if(e.toString().equals("Cannot Attend Contest. Contest for given id:"+contestID+" is in progress!")){
                System.out.println("Cannot Attend Contest. Contest for given id:"+contestID+" is in progress!");
            }
            else if(e.toString().equals("Cannot Attend Contest. Contest for given id:"+contestID+" is ended!")){
                System.out.println("Cannot Attend Contest. Contest for given id:"+contestID+" is ended!");
            }
            else if(e.toString().equals("Cannot Attend Contest. User for given contest id:"+contestID+" is already registered!")){
                System.out.println("Cannot Attend Contest. User for given contest id:"+contestID+" is already registered!");
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        

    }
    
}
