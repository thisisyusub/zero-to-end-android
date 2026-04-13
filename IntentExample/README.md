## Intent

At its simplest, an Intent is a messaging object you use to request an action 
from another app component. It is the glue that connects Activities, Services, 
and BroadcastReceivers

## What actually happens when you call startActivity(intent)?

1. IPC (Inter-Process Communication): An Intent is a Parcelable object. 
When you call startActivity(), the Intent is serialized and sent out of your app's process 
via the Binder (Android's IPC mechanism).
2. System Server (ATMS): The Intent reaches the ActivityTaskManagerService (ATMS) running inside 
the core Android OS system_server process.
3. Intent Resolution: If the Intent is Implicit, the ATMS asks the PackageManager to scan 
the AndroidManifest.xml of every installed app on the device. It looks for <intent-filter> tags 
that perfectly match your Intent's Action, Data (URI/MIME type), and Category
4. Process Forking: Once the OS finds the target Activity (whether it's in your app or a completely 
different app), it checks if that app's process is running. If not, it commands the 
Zygote process to fork a new Linux process for that app.
5. Delivery: Finally, the ATMS sends a Binder transaction down to the target app's ActivityThread, 
triggering onCreate() and handing it your Intent payload.

## What this example includes?

- Explicit Intent
- Implicit Intent
- Activity Result API
- View binding
- Parcelize

## Documentation
https://developer.android.com/guide/components/intents-filters