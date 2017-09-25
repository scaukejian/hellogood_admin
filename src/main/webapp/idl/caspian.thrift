namespace java im.link.caspian.ws.thrift

struct AuthToken {
	1: required string id;
	2: required string crypto;
}

struct GroupThrift {
	1: required string id;
	2: optional string name;
}

struct AuthResponseThrift {
	1: required i32 code;
	2: optional string message;
	3: optional AuthToken token;
	4: optional set<GroupThrift> groups;
}

struct UserThrift {
	1: required string id;
	2: required string account;
}

struct UserInfoThrift {
	1: required string user;
	2: optional set<GroupThrift> groups	;
}

struct PubMessageThrift {
	1: required string title;
	2: required string content;
	3: required GroupThrift group;
	4: optional i64 sendTime;
}

struct PubMessageResponseThrift {
	1: required i32 code;
	2: optional string message;
	3: optional PubMessageThrift data;
}

struct PubMessageListResponseThrift {
	1: required i32 code;
	2: optional string message;
	3: optional list<PubMessageThrift> data;
}

struct P2pMessageThrift {
	1: optional UserThrift userFrom;
	2: optional UserThrift userTo;
	3: required string content;
}

struct P2pMessageResponseThrift {
	1: required i32 code;
	2: optional string message;
	3: optional P2pMessageThrift data;
}

struct P2pMessageListResponseThrift {
	1: required i32 code;
	2: optional string message;
	3: optional list<P2pMessageThrift> data;
}

struct ServerResponseThrift {
	1: required i32 code;
	2: required string message;
}

service CaspianPushService {

	AuthResponseThrift login(1:string account,2:string password),
	
	PubMessageResponseThrift getPubMessage(1:AuthToken token,2:string id),
	
	P2pMessageResponseThrift getPubMessageReply(1:AuthToken token,2:string id),
	
	P2pMessageResponseThrift getP2pMessage(1:AuthToken token,2:string id),
	
	P2pMessageListResponseThrift getPubMessageReplyList(1:AuthToken token,2:string messageId,3:i32 pageNum,4:i32 pageSize),
	
	PubMessageListResponseThrift pullOffLinePubMessage(1:AuthToken token),
	
	P2pMessageListResponseThrift pullOffLinePubMessageReply(1:AuthToken token),
	
	P2pMessageListResponseThrift pullOffLineP2pMessage(1:AuthToken token),
	
	ServerResponseThrift sendMessageReply(1:AuthToken token, 2:string messageId,3:string content)

}

service CaspianBizService {

	AuthResponseThrift login(1:string account,2:string password),
	
	ServerResponseThrift publishMessage(1:AuthToken token,2:PubMessageThrift message),
	
	ServerResponseThrift sendP2pMessage(1:AuthToken token,2:string userId,3:string content),
	
	P2pMessageResponseThrift getPubMessageReply(1:AuthToken token,2:string id),
	
	P2pMessageListResponseThrift pullOffLinePubMessageReply(1:AuthToken token)

}