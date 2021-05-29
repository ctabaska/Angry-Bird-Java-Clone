public class Timer{
	double time;
	boolean start = false;
	public void startTimer(int startTimer){
		time = (double)startTimer;
		start = true;
	}
	public void runTimer(){
		if (start) {
			time += 0.15;
		}
	}
	public double getTime(){
		return time;
	}
	public void stopTimer(){
		start = false;
		time = 0;
	}
}