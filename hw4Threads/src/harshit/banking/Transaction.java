package harshit.banking;

public final class Transaction {

	private int SrcId;
	private int DestID;
	private int Amount;
	
//	public Transaction(int srcId, int destId, int amount){
	public Transaction(String rawLine){
			
		String[] raw_values = rawLine.split(" ");
		
//		System.out.println(raw_values.toString());
		
		try{
			SrcId = Integer.parseInt(raw_values[0]);
			DestID = Integer.parseInt(raw_values[1]);
			Amount = Integer.parseInt(raw_values[2]);			
		}catch(Exception ignoreException){
			System.err.print("Invalid Line ");
		}
	}

	protected synchronized int getSrcId() {
		return SrcId;
	}

	protected synchronized int getDestID() {
		return DestID;
	}

	protected synchronized int getAmount() {
		return Amount;
	}

	@Override
	public String toString() {
		String result = "SrcID :" + SrcId + " DestID :" + DestID + " Amount: " + Amount;
		return result;
	}
		
	
	
}
