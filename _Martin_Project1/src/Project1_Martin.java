/**
 * Programmer: Hunter Martin
 * Project #1
 * COSC 221 W'24 Dr.Poh
 * 2-6-24
 * 
 * Description: It is a calculator based application. It is controlled by a switch and if user presses 1 it converts a decimal number
 * into its signed magnitude, ones and two complement, and excess 128 notation. If the user goes through the switch and they select 2
 * it converts a 8 bit sequence of numbers into its corresponding signed magnitude, ones and twos complement, and excess 128 notation.
 * 
 */

import java.util.Scanner ;
public class Project1_Martin {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		//declarations
		int choice = -1 ;
		int num ;
		
		System.out.println("WELCOME TO NUMBER CONVERTER CALCULATOR!") ;
		System.out.println("BY HUNTER MARTIN") ;
		
		while(choice != 0 ) {
			menu() ;
			choice = input.nextInt() ;
		switch(choice) {
		case 0: 
			System.out.println("Thank you for using my program today!!") ;
			break ;
		
		case 1:
			menuForDecimalToBinary() ;
			System.out.print("Please enter your decimal integer. ") ;
			num = input.nextInt();
			
			//shows the user the signed magnitude, one and two complement num > 0 indicates that the number is positive. 
			// we also make sure the number is under 127 to make sure we don't exceed the 8-bit representation
			 if(num > 0 && num <= 127) {
				 int[] signedMag = signedMag(num) ;
				 System.out.println("The signed magnitude for " + num + "# ") ;
				 display(signedMag) ;
				 
				 //for ones and two complement the positive number will remain the same as signed magnitude
				 System.out.println("One's Complement for " + num + "# ") ;
				 display(signedMag) ;
				
				 System.out.println("Two's Complement for " + num + "# ") ;
				 display(signedMag) ;
				 
				 //excess 128
				 System.out.println("Excess 128 for " + num + "# ");
				 int excessNum = num + 128 ;
				 int[] excessSignedMag = signedMag(excessNum) ;
				 display(excessSignedMag) ;
				 break ;
			 
			 }
			 
			 //if the number they wanted is negative
			 else if(num < 0 && num >= -127){
				
				 //the method works exactly the same as signed magnitude for positive numbers as for negative numbers since it just prints 1 instead of changing the actual number
				 int[] signedMag = signedMag(num) ;
				 System.out.println("The signed magnitude for " + num + "# ") ;
				 System.out.print("1");
				 displayNegatives(signedMag) ;
				 
				 //one's complement for a negative number. The MSB will remain the same but the bits will flip.
				 System.out.println("The One's complement for " + num + "# ") ;
				 int[] oneComplements = oneComplement(num) ;
				 System.out.print("1");
				 int[] negOneComplement = displayNegatives(oneComplements) ;
				 
				
				 //now that we flipped everything we need to figure how to add 1 in binary logic.
				 int[] mask = {0, 0, 0, 0, 0, 0, 1};

				 //its 6-i because there are 7 elements but the 1 is at index 6
				 for (int i = 0; i < 8; i++) {
				 int sum = negOneComplement[i] + mask[6-i];
				    if(sum == 2) {
				    	negOneComplement[i] = 0;
				    	mask[(6-i) -1 ] = 1;
				   
				    	}
				    else {
				    	negOneComplement[i] = sum ;
				    	break;
				    }
				    }
				    
				 //displaying twos complement
				 System.out.println("The two's complement for " + num + "# ") ;
				 System.out.print("1") ;
				 displayNegatives(negOneComplement) ;
				 
				 
				 //excess 128 for negatives, since the result will always be positive we can just use the same method.
				 System.out.println("Excess 128 for " + num + "# ");
				 int excessNum = num + 128 ;
				 int[] excessSignedMag = signedMag(excessNum) ;
				 display(excessSignedMag);
				 break;
				 
			 }
			 
			 else {
				 System.out.println("Invalid the number is out bounds. It is between -127 and 128 for 8- bit representation.") ;
				 break;
			 }
			
			 
		case 2:
			menuForBinaryToDecimal() ;
			System.out.print("Please enter your 8-bit value. ");
			String eightBit = input.next();
	
			//case for where if the user entered more than 8 bits, or less than 8 bits
			if(eightBit.length() != 8){
				System.out.println("INVALID BIT PATTERN!! ONLY 8 BITS PLEASE!! ");
				break;
			}
			
			boolean validInput = true;
			//another check to make sure the bit pattern is only 1s and 0s
			for(int i = 0 ; i < eightBit.length(); i++) {
				char bit = eightBit.charAt(i) ;
				if(bit != '0' && bit != '1') {
					validInput = false;
					System.out.println("ONLY 1S AND 0S!!!");
					break;
				}
			} 
		
			//if the user entered all the correct criteria (aka no more than 8 digits and only 1s and 0s)
			if(validInput) {
			
			//converting the 8bit to an array of characters
			char[] binaryNums = eightBit.toCharArray() ;
			
			
			//since the 8 bit value is positive and it starts with a 0, it will be less than 1000000
			if(Integer.parseInt(eightBit) < 10000000) {
				int sum = 0 ;
				for(int i = 0 ; i < 8 ; i++) {
					int ValueOfBit = Character.getNumericValue(binaryNums[i]) ;
					sum += Math.pow(2, 7 - i ) * ValueOfBit;
				}
			//if its positive its going to be all be the same for ones complement, twos ,and signed magnitude.
			System.out.println("The signed integer for " + eightBit + " is: ") ;
			System.out.println(sum) ;
			System.out.println("The Ones Complement for " + eightBit + " is ");
			System.out.println(sum) ;
			System.out.println("The Twos Complement for " + eightBit + " is ") ;
			System.out.println(sum) ;
			
			//excess 128. Take the number we just found and minus 128 from it
			int excess128 = sum - 128 ;
			System.out.println("The excess 128 notation for " + eightBit + " is ");
			System.out.println(excess128) ;
			break;
			}
			else {
				
				//when the 8bit is negative signed integer
				int sum2 = 0 ;
				for(int i = 1 ; i < 8 ; i++) {
					int ValueOfBit = Character.getNumericValue(binaryNums[i]) ;
					sum2 += Math.pow(2, 7 - i ) * ValueOfBit;
					}
				System.out.println("The signed integer for " + eightBit + " is: ") ;
				System.out.println(-sum2) ;
				
				//ones complement for negatives
				//flip all the bits except the MSB
				//The MSB is printed so I dont have to worry abt 
				for(int i = 0 ; i < 8 ; i++) {
					if(binaryNums[i] == 0) {
						binaryNums[i] = 1 ;
					}
					else{
						binaryNums[i] = 0 ;
					}
				}
				
				//now that the bits are now flipped. We need to do the calculations. Luckily the signed integer equation works exactly the same.
				for(int i = 1 ; i < 8 ; i++) {
					int ValueOfBit = Character.getNumericValue(binaryNums[i]) ;
					sum2 += Math.pow(2, 7 - i ) * ValueOfBit;
					}
				System.out.println("The One's Complement for " + eightBit + " is: ");
				System.out.println(sum2);
				
				//twos complement is just one less than ones complement
				int twoComplement = sum2 - 1 ;
				System.out.println("The Two's Complement for " + eightBit + " is: " );
				System.out.println(twoComplement);
				
				//excess 128
				int excess128 = twoComplement + 128 ;
				System.out.println("Excess 128 notation for " + eightBit + " is: ");
				System.out.println(excess128) ;
				break;
				
			}
				}
			break;
			default:
				System.out.println("INVALID!!!! Please make a selection! 0,1, or 2 ");
				break;
				}                  
				}
		}
		
//methods

//menu for the user to show the selection they can make
public static void menu() {
	System.out.println("Please select a choice of either: 0,1, or 2") ;
	System.out.println("0. Quit") ;
	System.out.println("1. Converting Decimal Integer to Binary Representations");
	System.out.println("2. Turn a 8 bit value to the corresponding decimal values");
	System.out.println("-------------------------------------------------------------------------------------") ;
	}

//menu for the user to show them what is going to happen if you entered "1"
public static void menuForDecimalToBinary() {
	System.out.println("CONVERTING DECIMAL INTEGER TO BINARY REPRESENTATIONS") ;
	System.out.println("This program will show you the signed magnitude , ones and two complement, and excess 128 notation") ;
	System.out.println("-------------------------------------------------------------------------------------") ;
}

//menu for the user to show them if they entered "2"
public static void menuForBinaryToDecimal() {
	System.out.println("CONVERTING 8 BIT VALUE TO THE CORRESPONDING DECIMAL VALUES!!") ;
	System.out.println("This program will show you the signed magnitude , ones and two complement, and excess 128 notation") ;
	System.out.println("-------------------------------------------------------------------------------------") ;
}

//signed magnitude
public static int[] signedMag(int num) {
	int[] bits = new int[8] ;
	for(int i = 0 ; i < 8 ; i++) {
				bits[i] = num % 2 ;
				if(bits[i] < 0 ) {
					bits[i] = 1;
				}
				num /= 2 ;
			}
		return bits ;
	}

//prints the array which holds the bits. This is for positive numbers.
public static void display(int[] nums) {
	 for(int i = 7 ; i >= 0 ; i--) {
		 System.out.print(nums[i]);
	 }
	 System.out.println();
}

//prints the array which holds the bits. This is for negative numbers. Notice i used 6 instead of 7 because for negatives I printed out a 1 .
public static int[] displayNegatives(int[] nums) {
	 for(int i = 6 ; i >= 0 ; i--) {
		 System.out.print(nums[i]);
	 }
	 System.out.println();
	 return nums;
}

//method for ones complement
public static int[] oneComplement(int num) {
	int[] bits = new int[8] ;
	for(int i = 0 ; i < 8 ; i++) {
				bits[i] = num % 2 ;
				
				
				bits[i] = 1 ^ bits[i];
				
				//this was a weird case, since the number is negative, when the number is raised it results a negative
				if(bits[i] == -2) {
					bits[i] = 0 ;
				}
				//num = num / 2
				num /= 2 ;
			}
	
	 return bits ;
	}
}


