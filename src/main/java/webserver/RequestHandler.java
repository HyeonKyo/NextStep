package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.StartLine;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            //1. Start Line 파싱 -> Method, url, version
            String firstLine = br.readLine();
            StartLine startLine = HttpRequestUtils.parseStartLine(firstLine);
            //2. Header 파싱 ->

            //3. Data 파싱 -> readData 활용

            //4. 3개의 데이터 조합해서 할일 처리
            //- url이 webapp폴더에 있는 파일일 경우 -> 파일 읽기
            //- /create 등 원하는 기능인 경우 -> 해당 기능 실행, 응답 데이터 만들기
            byte[] body = "Hello World".getBytes();
            if (!"/".equals(startLine.getUrl())) {
                //body = file에서 읽어오기
                body = Files.readAllBytes(new File("./webapp" + startLine.getUrl()).toPath());
            }
            //5. 응답 데이터 출력

            //Output 만들기
            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
