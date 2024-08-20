## Project showcase Memory/CPU leak

# MemoryLeak - This project show some common situations that accidently enter memory leak.

- Passing context of a Activity/Fragment to other instance ( an object ) that live longer than it. 
- Inner class memory leak: since the inner class implicit hold the reference to the outter class, when outter class is destroyed but the inner class still have refernce to, it causes memory leak.
- Handler is not handle properly: The activit start the handler but when the activity is destroyed, the message in queue haven't complete
yet will cause memory leak.
- Viewbinding memory leak: the view binding is not set to null when the activity is destroy can cause memory leak.
- Work is running in View Model, when the VM enter the #ViewModel_onClear stage but the work is not completed ( API call ), it can cause memory leak.

# Cpu Leak
  
- A while loop can make CPU over_loaded. 
