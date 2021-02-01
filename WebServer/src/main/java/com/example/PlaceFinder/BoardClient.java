package com.example.PlaceFinder;

import com.ericsson.otp.erlang.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BoardClient {
    private final String serverNodeName = "board_server@localhost";
    private final String serverName = "board_server";
    private OtpMbox mbox;

    public BoardClient() {
        String nodeName = "board_client@localhost";
        String mBoxName = "board_client";
        String cookie = "board";
        try {
            OtpNode node = new OtpNode(nodeName, cookie);
            mbox = node.createMbox(mBoxName);
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public boolean insertMessage(String username, String message) {
        OtpErlangTuple request = new OtpErlangTuple(new OtpErlangObject[] {
                new OtpErlangAtom("$gen_call"),
                new OtpErlangTuple(new OtpErlangObject[] {
                        this.mbox.self(),
                        new OtpErlangAtom("nil")
                }),
                new OtpErlangTuple(new OtpErlangObject[] {
                        new OtpErlangAtom("insert"),
                        new OtpErlangString(username),
                        new OtpErlangString(message)
                })
        });
        mbox.send(serverName, serverNodeName, request);
        try {
            OtpErlangTuple response = (OtpErlangTuple) mbox.receive();
            String result = ((OtpErlangAtom) response.elementAt(1)).atomValue();
            return result.compareTo("success") == 0;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public boolean deleteMessage(long id) {
        OtpErlangTuple request = new OtpErlangTuple(new OtpErlangObject[] {
                new OtpErlangAtom("$gen_call"),
                new OtpErlangTuple(new OtpErlangObject[] {
                        this.mbox.self(),
                        new OtpErlangAtom("nil")
                }),
                new OtpErlangTuple(new OtpErlangObject[] {
                        new OtpErlangAtom("delete"),
                        new OtpErlangLong(id)
                })
        });
        mbox.send(serverName, serverNodeName, request);
        try {
            OtpErlangTuple response = (OtpErlangTuple) mbox.receive();
            String result = ((OtpErlangAtom) response.elementAt(1)).atomValue();
            return result.compareTo("success") == 0;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    private Message tupleToMessage(OtpErlangTuple tuple) {
        Message message = new Message();
        message.setId(((OtpErlangLong) tuple.elementAt(0)).longValue());
        message.setUsername(((OtpErlangString) tuple.elementAt(1)).stringValue());
        message.setMessage(((OtpErlangString) tuple.elementAt(3)).stringValue());
        OtpErlangTuple date = ((OtpErlangTuple) ((OtpErlangTuple) tuple.elementAt(2)).elementAt(0));
        OtpErlangTuple time = ((OtpErlangTuple) ((OtpErlangTuple) tuple.elementAt(2)).elementAt(1));
        String dateTime = "";
        for (int i = 2; i >= 0; i--) {
            String element = date.elementAt(i).toString();
            if (element.length() == 1)
                dateTime += "0" + element;
            else
                dateTime += element;
            if (i != 0)
                dateTime += "/";
        }
        dateTime += " ";
        for (int i = 0; i < 3; i++) {
            String element = time.elementAt(i).toString();
            if (element.length() == 1)
                dateTime += "0" + element;
            else
                dateTime += element;
            if (i != 2)
                dateTime += ":";
        }
        message.setDateTimeFromString(dateTime);
        return message;
    }

    public List<Message> readMessages(int limit) {
        OtpErlangTuple request = new OtpErlangTuple(new OtpErlangObject[] {
                new OtpErlangAtom("$gen_call"),
                new OtpErlangTuple(new OtpErlangObject[] {
                        this.mbox.self(),
                        new OtpErlangAtom("nil")
                }),
                new OtpErlangTuple(new OtpErlangObject[] {
                        new OtpErlangAtom("read"),
                        new OtpErlangInt(limit)
                })
        });
        mbox.send(serverName, serverNodeName, request);
        List<Message> result = null;
        try {
            OtpErlangTuple response = (OtpErlangTuple) mbox.receive();
            if (response.elementAt(1) instanceof OtpErlangList) {
                OtpErlangList resultList = (OtpErlangList) response.elementAt(1);
                result = new ArrayList<Message>();
                for (OtpErlangObject otpErlangObject : resultList) {
                    OtpErlangTuple tuple = (OtpErlangTuple) otpErlangObject;
                    Message message = tupleToMessage(tuple);
                    result.add(message);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }
}
