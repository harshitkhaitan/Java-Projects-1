import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CountDownLatch;



public class Cracker {
	// Array of chars used to produce strings
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();	
	public static final int MAX_THREADS = 40;
	private String GeneratedhashedPassword;
	private StringBuffer GeneratedPassword;
	private String HashedPassword;
	private int PasswordLength;
//	private int totalIterationsPerIndex;
	private Worker[] workers;
	private CountDownLatch startSignal;
    private CountDownLatch doneSignal;
	
	public Cracker(String password) {
		byte[] passwordBytes = password.getBytes();
		byte[] result = Generator(passwordBytes);
		GeneratedhashedPassword = hexToString(result);
		System.out.println(GeneratedhashedPassword);
 	}
	
	public Cracker(String hashedPassword, int passwordLength, int totalThreads) {
		this.HashedPassword = hashedPassword;
		int TotalThreads = Math.min(totalThreads, MAX_THREADS);
		this.PasswordLength = passwordLength;
//		this.totalIterationsPerIndex = (int) Math.pow(CHARS.length, (PasswordLength-1)) ;
		GeneratedPassword = new StringBuffer();
		workers = new Worker[TotalThreads];
		startSignal = new CountDownLatch(1);
		doneSignal = new CountDownLatch(TotalThreads);
		for(int i=0; i<TotalThreads;i++){
			int firstIndex = (int) Math.ceil((double) CHARS.length*i/TotalThreads);
			int lastIndex = (int) Math.ceil((double) CHARS.length*(i+1)/TotalThreads)-1;			
//			System.out.print("Index " + firstIndex + " " + lastIndex + " String Len " + CHARS.length + " vs " + Math.ceil(  ((i+1) * CHARS.length/TotalThreads)   ) + "Iterations" + totalIterationsPerIndex +"\n");
			workers[i] = new Worker(firstIndex, lastIndex);
			workers[i].start();
		}
		startSignal.countDown();
		try {
			doneSignal.await();
			System.out.println(GeneratedPassword.toString());
			System.out.println("All Done");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	private class Worker extends Thread{
		private int FirstIndex, LastIndex;
		private Worker(int firstIndex, int lastIndex){
			super();
			this.FirstIndex = firstIndex;
			this.LastIndex = lastIndex;
		}
		
		@Override
		public void run() {
			try {
				startSignal.await();
				for(int passwordLength=1;passwordLength<=PasswordLength;passwordLength++){
					for(int j=FirstIndex;j<=LastIndex;j++){
						StringBuffer buff = new StringBuffer();
						buff.append(CHARS[j]);
						int depth = 0;
						loop(depth,buff, passwordLength);
					}
				}	
				
			} catch (InterruptedException e) {
			}
			
			doneSignal.countDown();
		}
	
		private void compute(StringBuffer buff){
//			System.out.println(" This thread: " + Thread.currentThread().getName() + " String " + buff.toString());
			byte[] resultByte = Generator(buff.toString().getBytes());
			String resultHash = hexToString(resultByte);
			if(resultHash.equals(HashedPassword)) SetGeneratedPassword(buff.toString());
		}

		private void loop(int depth, StringBuffer buff, int passwordLength){
			if(depth == (passwordLength-1)){
				compute(buff);
			}else{
				for(int i=0;i<CHARS.length;i++){
					if(buff.length()>(depth+1)) {
						buff.setCharAt(depth+1, CHARS[i]);
					}else{
						buff.append(CHARS[i]);
					}
					loop(depth+1,buff,passwordLength);
				}			
			}
		}
	}
	
	
	
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	/*
	 Given a string of hex byte values such as "24a26f", creates
	 a byte[] array of those values, one byte value -128..127
	 for each 2 chars.
	 (provided code)
	*/
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length()/2];
		for (int i=0; i<hex.length(); i+=2) {
			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
		}
		return result;
	}
	
	public synchronized void SetGeneratedPassword(String password){
		this.GeneratedPassword.append(password);
	}
	
	
	public byte[] Generator(byte[] password) {
		byte[] hashedPassword;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(password);
			hashedPassword = md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		return hashedPassword;
	}

	// possible test values:
	// a 86f7e437faa5a7fce15d1ddcb9eaeaea377667b8
	// fm adeb6f2a18fe33af368d91b09587b68e3abcb9a7
	// a! 34800e15707fae815d7c90d49de44aca97e2d759
	// xyz 66b27417d37e024c46526c2f6d358a754fc552f3

	
	
	public static void main(String[] args){
		@SuppressWarnings("unused")
		Cracker myCracker;
		if(args.length == 1){
			myCracker = new Cracker(args[0]);
		}else if(args.length == 3){
			try{
				Integer len = Integer.parseInt(args[1]);
				Integer threads = Integer.parseInt(args[2]);
				myCracker = new Cracker(args[0], len, threads);
			}catch(Exception igException){
				System.err.println("Bad Command Line Options");
			}
		}
		
		
	}

	public String getGeneratedhashedPassword() {
		return GeneratedhashedPassword;
	}

	protected synchronized String getGeneratedPassword() {
		return GeneratedPassword.toString();
	}

	
}
