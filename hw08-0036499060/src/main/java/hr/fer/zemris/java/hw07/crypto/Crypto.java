package hr.fer.zemris.java.hw07.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class used for checking the checksum of a file, or encrypting and decrypting
 * files. Usage explained in the main method documentation.
 * 
 * @author MarinoK
 */
public class Crypto {

	/**
	 * Scanner used to read system input and communicate with the user. Is a static
	 * final constant because it is accessed within multiple different methods.
	 */
	private static final Scanner sc = new Scanner(System.in);

	/** String constant that marks checkSHA mode of file processing. */
	private static final String CHECKSHA = "checksha";

	/** String constant that marks encrypt mode of file processing. */
	private static final String ENCRYPT = "encrypt";

	/** String constant that marks decrypt mode of file processing. */
	private static final String DECRYPT = "decrypt";

	/** Number of arguments that is required to encrypt or decrypt a file. */
	private static final int CRYPT_ARGUMENTS = 3;

	/** Number of arguments that is required to check SHA of a file. */
	private static final int CHECKSHA_ARGUMENTS = 2;

	/**
	 * Main method runs when the program is run.
	 * 
	 * @param args
	 *            expected two or three arguments. If the first argument is
	 *            checksha, second argument must be path to the file whose checksum
	 *            should be checked. In case the first argument is encrypt/decrypt,
	 *            second argument must be path to the file to be
	 *            encrypted/decrypted, and the third argument must be the
	 *            destination path. These are the only supported cases.
	 */
	public static void main(String[] args) { //test files are stored in the resources folder!
		File inputFile = null;
		File outputFile = null;

		if (args.length < CHECKSHA_ARGUMENTS || args.length > CRYPT_ARGUMENTS) {
			throw new CryptoArgumentsException();
		}

		switch (args[0].toLowerCase().trim()) {
		case (CHECKSHA): {
			if (args.length != CHECKSHA_ARGUMENTS) throw new CryptoArgumentsException();
			inputFile = new File(args[1]);
			checkFile(inputFile);
			try {
				digest(inputFile);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			break;
		}
		case (ENCRYPT): {
			if (args.length != CRYPT_ARGUMENTS) throw new CryptoArgumentsException();
			inputFile = new File(args[1]);
			outputFile = new File(args[2]);
			checkFile(inputFile);
			crypt(inputFile, outputFile, true);
			break;
		}
		case (DECRYPT): {
			if (args.length != CRYPT_ARGUMENTS) throw new CryptoArgumentsException();
			inputFile = new File(args[1]);
			outputFile = new File(args[2]);
			checkFile(inputFile);
			crypt(inputFile, outputFile, false);
			break;
		}
		default:
			throw new CryptoArgumentsException();
		}
	}

	/**
	 * Auxiliary method used to check whether the input file is valid for
	 * processing.
	 * 
	 * @param inputFile
	 *            file to check
	 * @throws CryptoArgumentsException
	 *             if the file does not exist, is not a directory, or can not be
	 *             read
	 */
	private static void checkFile(File inputFile) {
		if (!inputFile.exists() || !inputFile.canRead() || inputFile.isDirectory()) {
			throw new CryptoArgumentsException("Invalid file.");
		}
	}

	/**
	 * Method used to encrypt or decrypt the given inputFile into outputFile.
	 * Communicates with the user through the System.in, expects password and
	 * initialization vector for the encryption, both 32 hex-digits. Whether the
	 * method is used for encrypting or decrypting is determined by the given
	 * boolean.
	 * 
	 * @param inputFile
	 *            file to en/decrypt
	 * @param outputFile
	 *            file to en/decrypt into
	 * @param encrypt
	 *            if true, file will be encrypted, else it will be decrypted
	 */
	private static void crypt(File inputFile, File outputFile, boolean encrypt) {

		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		System.out.print("> ");
		String keyText = sc.nextLine();
		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		System.out.print("> ");
		String ivText = sc.nextLine();
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException | NoSuchAlgorithmException
				| NoSuchPaddingException e) {
			e.printStackTrace();
		}

		try (InputStream is = new BufferedInputStream(Files.newInputStream(inputFile.toPath()));
				OutputStream os = new BufferedOutputStream(Files.newOutputStream(outputFile.toPath()))) {
			byte[] outputBuffer;
			byte[] inputBuffer = new byte[4096];
			while (true) {
				inputBuffer = new byte[4096];
				int r = is.read(inputBuffer);
				if (r < 1) break;
				outputBuffer = cipher.update(inputBuffer, 0, r);
				os.write(outputBuffer);
			}
			byte[] finisher = cipher.doFinal();
			os.write(finisher, 0, finisher.length);

		} catch (IOException | IllegalBlockSizeException | BadPaddingException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Method used to read the input file, and calculate its checksum. Communicates
	 * with the user through System.in, expects the SHA-256 digest to compare it
	 * with the calculated checksum. Writes the appropriate message to the user,
	 * depending on the equality of the strings.
	 * 
	 * @param inputFile
	 *            file whose checksum is to be calculated
	 */
	private static void digest(File inputFile) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException ignorable) {
			System.out.println("ignorable");
		}
		System.out.println("Please provide expected sha-256 digest for " + inputFile.getName() + ":");
		System.out.print("> ");
		String expected = sc.nextLine().trim().toLowerCase();

		try (InputStream is = Files.newInputStream(inputFile.toPath())) {
			while (true) {
				byte[] inputBuffer = new byte[4096];
				int r = is.read(inputBuffer);
				if (r < 1) break;
				md.update(inputBuffer, 0, r);
			}
		} catch (IOException e) {
			System.out.println(e.getStackTrace());
		}
		String checksum = Util.bytetohex(md.digest());
		if (checksum.equals(expected)) {
			System.out.println("Digesting completed. Digest of " + inputFile.getName() + " matches expected digest.");
		} else {
			System.out.println("Digesting completed. Digest of " + inputFile.getName()
					+ " does not match the expected digest. Digest was: " + checksum);
		}
	}
}
