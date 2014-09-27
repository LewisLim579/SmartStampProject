import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class GCMServerSide {

	public void sendMessage() throws IOException {
		
		connmysql db = new connmysql();
		
		ArrayList<String> id = db.testMySql();
		

		Sender sender = new Sender("AIzaSyBRnqxF6azkkRdRWl4DmSPegIkBRTFEaHA");

		
		String sandTitle = "스마트스탬프 이벤트";
		String sandMsg = "13~14시 할인행사 시작합니다.";
		  
		Message message = new Message.Builder()
		.addData("title",URLEncoder.encode(sandTitle, "euc-kr"))
		.addData("msg",URLEncoder.encode(sandMsg, "euc-kr"))
		.build();

		List<String> list = new ArrayList<String>();

		for(int i=0;i<id.size();i++){
			String regId = id.get(i).toString();
			list.add(regId);
		}
		System.out.println(list.get(1));
		
		//諛쒖넚��硫붿떆吏� 諛쒖넚����퉫(RegistrationId, Retry �잛닔)
		MulticastResult multiResult = sender.send(message, list, 5);

		if (multiResult != null) {

			List<Result> resultList = multiResult.getResults();

			for (Result result : resultList) {

				System.out.println(result.getMessageId());

			}

		}

	}

	public static void main(String[] args) throws Exception {

		GCMServerSide s = new GCMServerSide();

		s.sendMessage();

	}

}