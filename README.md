# DeepLinkSample

Sample code for using DeepLink in Android

# Getting Started

* Chúng ta cần tạo 1 đường link mà khi click vào đó có thể vào được trực tiếp phần nào trong ứng dụng của mình.
* Đường link này có thể chứa dữ liệu trong đó và sẽ được gửi tới ứng dụng thông qua Intent

## DeepLink structure

DeepLink dựa trên **intent-filter** để có thể lắng nghe những sự kiện của intent hệ thống phát ra và mở ra theo đúng **action** mà bạn chọn.

Như vậy việc triển khai của DeepLink sẽ diễn ra ở trong file AndroidManifest.xml như sau:

```
<activity
  android:name=".DetailActivity"
  android:label="@string/app_name"
  android:launchMode="singleTop">
  <intent-filter android:label="@string/detail_title">
    <action android:name="android.intent.action.VIEW" />

    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />
    
    <data
      android:host="ohoangngocthai.github.io"
      android:pathPrefix="/DeepLinkSample"
      android:scheme="https" />
  </intent-filter>
</activity>
```
Với DeepLink tạo demo ở đây: https://ohoangngocthai.github.io/DeepLinkSample/

Trong đó cần chú ý đến thẻ **data** bao gồm:
* **scheme**: Là phần thiết yếu nhất để xác định một URI, ít nhất phải có một thuộc tính scheme nằm trong bộ lọc intent-filter. Có thể đặt là http để tiếp nhận link từ web.
* **host**: Thuộc tính này là phần xác định tên miền của đường link. Nó chỉ có ý nghĩa khi thuộc tính schema được thêm vào. Thông thường sẽ được chỉ định một cách tường mình, nhưng nếu bạn muốn khớp nhiều tên miền thì nên sử dụng dấu * để  tượng trưng cho một nhóm tên miền. Ví dụ như *google.com sẽ tượng trưng cho www.google.com, develop.google.com, ....
* **port**: Thuộc tính này tượng trung cho cổng của URI. Nó chỉ có ý nghĩa khi thuộc tính schema và host được chỉ định và thường được bắt đầu bằng **/**.
  * **path**: Ứng với đường dẫn hoàn chỉnh của đường link mà intent gửi đến
  * **pathPrefix**: Ứng với đường dẫn khớp với phần đầu của đường link.
  * **pathPattern**: Ứng với đường dẫn hoàn chỉnh của đường link nhưng mà nó có thể thêm ký hiệu để biểu tượng cho một ký tự nào đó. Ví dụ như nếu để là (*) thì đại diện cho một chuỗi từ 0 đến nhiều ký tự đã xuất hiện trước đó. Còn để là (.*) thì đại diện cho bất kì chuỗi nào từ 0 đến nhiều ký tự.

## Implement

1. Tạo một **DeepLink* để sử dụng
Ở đây sử dụng **Github Page* để tạo một host để test. Ở đây mình sử dụng đường link này:
https://ohoangngocthai.github.io/DeepLinkSample/
2. Thiết lập bộ lọc **intent-filter** để đón nhận những hoạt động từ intent gửi về.
```
<activity
  android:name=".DetailActivity"
  android:label="@string/app_name"
  android:launchMode="singleTop">
  <intent-filter android:label="@string/detail_title">
    <action android:name="android.intent.action.VIEW" />

    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />
    
    <data
      android:host="ohoangngocthai.github.io"
      android:pathPrefix="/DeepLinkSample"
      android:scheme="https" />
  </intent-filter>
</activity>
``` 
Chúng ta nên để Action View để tích hợp được việc tìm kiếm của Google. Category cũng thường được để mặc định là DEFAULT hoặc là BROWSABLE.

3. Nhận dữ liệu
Chúng ta nhận dữ liệu của data gửi về là một URI, nên để lấy được dữ liệu chứa trong đường link thì bạn chỉ cần cắt chuỗi để tìm được dữ liệu cần thiết từ **DeepLink** gửi về và hiển thị trên Activity đón nhận nó.
```
val action = intent.action
val data = intent.dataString
```
# Running the tests
Có 2 cách để test được DeepLink có thể kể đến là sử dụng **ADB** hoặc là trực tiếp sử dụng link đó trên các ứng dụng khác hoặc trên web để có thể mở ứng dụng có trong máy.
* **ADB**: Sử dụng adb để test,chỉ cần mở terminal của Android Studio lên và gõ lệnh sau:
```
adb shell am start -a android.intent.action.VIEW -d "https://ohoangngocthai.github.io/DeepLinkSample/"
```
* Test trực tiếp là gửi đường link này qua tin nhắn, gmail, web thì khi click vào sẽ mở được ứng dụng với màn hình đón nhận nó.


