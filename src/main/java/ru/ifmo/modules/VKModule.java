package ru.ifmo.modules;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import ru.ifmo.models.DontGetMessage;
import ru.ifmo.models.Message;
import ru.ifmo.models.interfaces.IRequestModule;

import java.util.List;
import java.util.Random;

public class VKModule implements IRequestModule {
    private final Random random = new Random();
    private VkApiClient vk;
    private int ts;
    private GroupActor actor;
    private int maxMsgId = -1;
    static {

    }

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
    public Message getMessage() throws DontGetMessage {
        try {
            MessagesGetLongPollHistoryQuery eventsQuery = vk.messages()
                    .getLongPollHistory(actor)
                    .ts(ts);
            if (maxMsgId > 0) {
                eventsQuery.maxMsgId(maxMsgId);
            }
            List<com.vk.api.sdk.objects.messages.Message> messages = eventsQuery
                    .execute()
                    .getMessages().getItems();

            if (!messages.isEmpty()) {
                try {
                    ts = vk.messages()
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
                if (messageId > maxMsgId) {
                    maxMsgId = messageId;
                }

                return toMessage(messages.get(0));
            }
            return null;
        }
        catch (Exception e){
            throw new DontGetMessage(e.getMessage());
        }
    }

    private Message toMessage(com.vk.api.sdk.objects.messages.Message message) {
        Message res = new Message(message.getText(), message.getPeerId());
        res.setPhoto(message.getAttachments().toString());
        return res;
    }

    public void sendMessage(Message message){
        if (message == null){
            System.out.println("null");
            return;
        }
        try {
            vk.messages().send(this.actor).peerId(message.getPeerId()).message(message.getText()).randomId(random.nextInt()).execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }
}
