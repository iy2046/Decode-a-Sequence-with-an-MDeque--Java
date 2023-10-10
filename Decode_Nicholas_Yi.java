package project3;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

/**
 * This program process a sequence of numbers according to given instructions.
 * The sequence of values provided by the user is a comma and space separated list of integers. For example: {@code 12, 43, 189, 42, 1, 35}.
 * The instructions consist of characters 'F', 'B' and 'R':
 * - F - drop the first element of the sequence
 * - B - drop the last element of the sequence
 * - R - reverse the sequence.
 *
 * The program outputs the resulting numerical sequence after the instructions were processed.
 *
 * For example, if the initial sequence is {@code 12, 43, 189, 42, 1, 35} and the instructions are {@code FRB}, the resulting sequence should be
 * {@code [35, 1, 42, 189]}.
 *
 * @author Joanna Klukowska
 * @author Nick Yi
 *
 */
public class Decode {

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);

		System.out.println("Enter the sequence to process: ") ;
		String sequence = in.nextLine();

		System.out.println("Enter the sequence of instrucitons: ");
		String instructions = in.nextLine();

		if (! isValid(instructions) ) {
			System.out.println("ERROR: instruction sequence is invalid.");
			System.exit(3); 
		}

		MDeque<Integer> list = null;

		try {
			list = parseSequence (sequence);
		}
		catch(IllegalArgumentException ex){
			System.out.println("Invalid numerical sequence. ") ;
			System.exit(1);
		}

		try {
			decode( list , instructions);
		}
		catch (NoSuchElementException ex ) {
			System.out.println("Instructions are invalid for the given sequence.");
			System.exit(2);
		}

		System.out.println("Decoded sequence is: ");
		System.out.println(list);

		in.close();

	}

	/**
	 * Decode the sequence represented by the {@code list} mdeque following the
	 * {@code instructions}.
	 * @param list the mdeque with sequence to decode
	 * @param instructions instructions to follow to decode the {@code list}
	 * @throws NoSuchElementException when the sequence is empty and the next instruction
	 * is either 'F' or 'B'
	 */
	public static void decode (MDeque<Integer> list , String instructions) throws NoSuchElementException { //decodes the given sequence following the decoding key
		
		// iterate through the instructional decoding key and execute the instruction(s)
		for (int i=0; i < instructions.length(); i++) {
			
			// drop/remove the front/first element in the sequence
			if (instructions.charAt(i) == 'F') {
				
				// instruction F cannot be applied to an empty sequence and will produce an appropriate error message
				if (list.size() == 0) {
					throw new NoSuchElementException("Instruction called on empty sequence."); 
				}
				list.popFront();
			}
			
			// drop/remove the back/last element in the sequence 
			else if (instructions.charAt(i) == 'B') {
				
				// instruction B cannot be applied to an empty sequence and will produce an appropriate error message
				if (list.size() == 0) {
					throw new NoSuchElementException("Instruction called on empty sequence.");
				}
				list.popBack();
			}
			
			// reverse the sequence
			else if (instructions.charAt(i) == 'R') {
				list.reverse();
			}
		}
	}

	/**
	 * Convert a given sequence from string format to mdeque of interger values.
	 * @param sequence string with comma and space separated values
	 * @return mdeque with the same values as the ones listed in the {@code sequence}
	 * @throws IllegalArgumentException when the sequence contains values that cannot be
	 * converted to a list of integer due to invalid characters or invalid separators
	 */
	public static MDeque<Integer> parseSequence (String sequence ) throws IllegalArgumentException {
		MDeque<Integer> list = new MDeque<>();
		try {
			String [] splitSequence = sequence.split(", ");

			for (int i = 0; i < splitSequence.length; i++ ) {
				list.pushBack(Integer.parseInt( splitSequence[i]) );
			}

		}
		catch (PatternSyntaxException ex ) {
			System.err.println("THIS SHOULD NOT HAPPEN!");
		}
		catch (NumberFormatException ex ) {
			throw new IllegalArgumentException ( "invalid value in the sequence");
		}
		return list;

	}


	/**
	 * Determines if the sequence of instructions is valid. A valid sequence consists of characters:
	 * 'R', 'F' and 'B' in any order.
	 * @param instructions instruction string
	 * @return {@code true} if instructions are valid, {@code false} otherwise
	 */
	public static boolean isValid ( String instructions ) {
		
		// handling the case where instructions string is null
		if (instructions == null) {
			return false;
		}
		
		// base case is when the instructions string is empty
		if (instructions.length() == 0) {
			return true;
		}
		
		// recursively validate each beginning character of the instructions string
		if ( (instructions.charAt(0)=='F') || (instructions.charAt(0)=='B') || (instructions.charAt(0)=='R') ) {
			return isValid(instructions.substring(1));
		}
		return false;
	}
}

