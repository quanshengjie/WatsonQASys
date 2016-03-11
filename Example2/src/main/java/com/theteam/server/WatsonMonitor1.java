package com.theteam.server;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;

public class WatsonMonitor1 implements IWatsonMonitor {

	// interval in unit of sec
	private final int INTERVAL = 60000;
	private final int INTERVAL_VARIANCE = 30000;
	private enum WATSON_STATUS
	{
		UP, STRANGE, DOWN
	}
	
	private Timer timer;
	private IWatsonPinger watsonPinger = new WatsonPinger1();
	private WATSON_STATUS watsonStatus = WATSON_STATUS.DOWN;
	
	WatsonMonitor1()
	{
	}
	
	public void Init() {
		Random generator = new Random(System.currentTimeMillis());
		timer = new Timer("WatsonMonitorTimer", true);
		int timeInterval = INTERVAL + generator.nextInt(INTERVAL_VARIANCE);
		System.out.println("Checking interval is " + timeInterval + " sec");
		timer.scheduleAtFixedRate(new WatsonMonitorTask(), 0, timeInterval);
	}
	
	private void ChangeStatus(WATSON_STATUS status)
	{
		if(this.watsonStatus != status)
		{
			switch(status)
			{
			case STRANGE:
				NotificationMailSender.Instance.simpleSendStrange();
				break;
			case DOWN:
				NotificationMailSender.Instance.simpleSendDown();
				break;
			case UP:
				NotificationMailSender.Instance.simpleSendUp();
				break;
			default:
				break;	
			}
			System.out.println("Change status to " + status.toString());
			this.watsonStatus = status;
		}
	}

	public void TestWatson()
	{
		System.out.println("Now checking Watson Status by "+ this.toString());
        int statusCode = watsonPinger.lightPing();
        if(statusCode / 100 == 2)
        {
       	 ChangeStatus(WATSON_STATUS.UP);
        }
        else
        {
       	 int statusCode1 = watsonPinger.heavyPing();
       	 int statusCode2 = watsonPinger.heavyPing();
       	 int statusCode3 = watsonPinger.heavyPing();
       	 if(statusCode1 / 100 != 2 && statusCode2 / 100 != 2 && statusCode3 / 100 != 2)
       	 {
       		 if (statusCode / 100 == 5)
                {
               	 ChangeStatus(WATSON_STATUS.DOWN);
                }
                else
                {
               	 ChangeStatus(WATSON_STATUS.STRANGE);
                }
       	 }
        }
	}
	
	public void Stop()
	{
		timer.cancel();
	}
	
	public class WatsonMonitorTask extends TimerTask {

        @Override
        public void run() {
        	 TestWatson();
        }

   }
}
