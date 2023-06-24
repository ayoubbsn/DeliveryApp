import android.annotation.SuppressLint
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        // Check if the message contains data
        if (remoteMessage.data.isNotEmpty()) {
            // Handle the data
            val deliveryStatus = remoteMessage.data["status"]

            // Trigger notification based on the status
            triggerNotification(deliveryStatus)
        }
    }

    private fun triggerNotification(status: String?) {
        // Implement your notification trigger here
    }
}
