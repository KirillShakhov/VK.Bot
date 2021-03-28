package ru.ifmo.modules;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import ru.ifmo.models.ResponseMessage;
import ru.ifmo.interfaces.IRequestModule;

import java.util.List;

public class VKModule implements IRequestModule {
    private VkApiClient vk;
    private static int ts;
    private GroupActor actor;
    private static int maxMsgId = -1;

    public VKModule(String access_token, String groupId) throws ClientException, ApiException {
        TransportClient transportClient = HttpTransportClient.getInstance();
        vk = new VkApiClient(transportClient);
        actor = new GroupActor(Integer.parseInt(groupId), access_token);
        ts = vk.messages().getLongPollServer(actor).execute().getTs();
    }

    public GroupActor getActor() {
        return actor;
    }
    public VkApiClient getVk() {
        return vk;
    }
    public Message getMessage() throws ClientException, ApiException {
        MessagesGetLongPollHistoryQuery eventsQuery = vk.messages()
                .getLongPollHistory(actor)
                .ts(ts);
        if (maxMsgId > 0){
            eventsQuery.maxMsgId(maxMsgId);
        }
        List<Message> messages = eventsQuery
                .execute()
                .getMessages()
                .getMessages();

        if (!messages.isEmpty()){
            try {
                ts =  vk.messages()
                        .getLongPollServer(actor)
                        .execute()
                        .getTs();
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
        if (!messages.isEmpty() && !messages.get(0).isOut()) {
                /*
                messageId - максимально полученный ID, нужен, чтобы не было ошибки 10 internal server error,
                который является ограничением в API VK. В случае, если ts слишком старый (больше суток),
                а max_msg_id не передан, метод может вернуть ошибку 10 (Internal server error).
                 */
            int messageId = messages.get(0).getId();
            if (messageId > maxMsgId){
                maxMsgId = messageId;
            }

            return messages.get(0);
        }
        return null;
    }

    public void sendMessage(ResponseMessage message){
        if (message == null){
            System.out.println("null");
            return;
        }
        try {
            vk.messages().send(this.actor).peerId(message.getPeerId()).message(message.getText()).execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }
}
