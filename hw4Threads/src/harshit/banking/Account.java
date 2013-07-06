package harshit.banking;

public class Account {

private int Id;	
private int Balance;
private int trans;
	
	public Account(int id, int balance){
		this.Id = id;
		this.Balance = balance;
		this.trans = 0;
	}

	protected synchronized int getId() {
		return Id;
	}

	protected synchronized void setId(int id) {
		Id = id;
	}

	protected synchronized int getBalance() {
		return Balance;
	}

	protected synchronized void setBalance(int balance) {
		Balance = balance;
	}

	protected synchronized void withdraw(int amount) {
		Balance = Balance - amount;
		trans++;
	}

	protected synchronized void deposit(int amount) {
		Balance = Balance + amount;
		trans++;		
	}

	@Override
	public String toString() {
		String result = "acct:" + Id + " bal:" + Balance + " trans:" + trans + "\n";
		return result;
	}
	
	
	
}
