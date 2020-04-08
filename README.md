# IF3210-2020-18-BolaSepak

<div>
BolaSepak adalah sebuah aplikasi yang memberikan informasi terkini mengenai sepakbola. Fitur utama dari BolaSepak adalah memberikan schedule pertandingan sepak bola yang akan datang, dan juga yang sudah lewat. Selain schedule BolaSepak juga dapat menunjukkan lokasi pertandingan sepak bola dan juga cuaca di lokasi pertandingan tersebut. Selain itu pengguna juga dapat melihat profil sebuah tim beserta pertandingan yang pernah dijalani. Pengguna dapat subscribe pada sebuah tim dan akan mendapatkan notifikasi ketika tim tersebut mengikuti pertandingan baru. Selain itu, untuk mendorong semangat untuk lebih sering berolahraga, aplikasi BolaSepak juga memiliki sebuah step counter untuk menghitung berapa langkah yang telah diambil asisten yang direset setiap harinya.
</div>

## Cara Kerja 
1. Pengguna dapat melakukan pencarian pertandingan berdasarkan nama tim.
Pencarian harus dilakukan secara real-time. (Hasil berubah seiring pengguna
memasukkan kata kunci pencarian).
2. Pengguna dapat melihat schedule pertandingan sepak bola, dan cuaca di lokasi
pertandingan tersebut.
3. Pengguna dapat melihat informasi pertandingan yang sudah/belum diadakan.
Informasi yang ditampilkan adalah logo masing-masing tim dan tanggal
pertandingan.
3.1 Untuk pertandingan yang sudah diadakan, informasi tambahan yang
ditampilkan adalah skor, jumlah tembakan untuk tim home dan away, dan
pencetak gol dan juga menit tercetaknya gol untuk tim home dan away.
3.2 Untuk pertandingan yang belum diadakan, informasi tambahan yang
ditampilkan adalah cuaca di lokasi pertandingan, pada jam pertandingan
berlangsung.
4. Pengguna dapat melihat jumlah langkah yang telah dilakukan setiap harinya.
Langkah pengguna tetap terhitung walaupun aplikasi tidak terbuka. 
5. Pengguna dapat subscribe pada sebuah tim sepakbola.
6. Pengguna dapat menerima notifikasi jika tim sepakbola yang di subscribe akan
bertanding.
7. Bila perangkat tidak terkoneksi internet, aplikasi dapat menampilkan data
pertandingan yang diambil dari cache.

## CPU Optimization
Salah satu optimisasi CPU yang dilakukan adalah pada saat aplikasi melakukan searching pada pemain sepak bola. Pada saat belum dijalankan optimisasi dan dilakukan pengetikan pada input field adalah:

![Sebelum Optimisasi](/spike.PNG)

Terlihat bahwa penggunaan CPU resource mencapai angka yang sangat tinggi hanya untuk penekanan 1 tombol pada searching. Hal ini disebabkan oleh pencarian yang tidak dioptimize. Aplikasi kami melakukan searching ke seluruh data tetapi tidak  menentukan batas maksimal untuk data yang ditampilkan yang menyebabkan bloking. Akhirnya kami memutuskan untuk mencantumkan 11 hasil teratas. Setelah tuning maka CPU profiler menampilkan gambar sebagai berikut.

![Setelah Optimisasi](/tuned.PNG)

Terlihat bahwa ada perbedaan signifikan pada saat tombol ditekan.

## Memory Optimization
Optimisasi Memory usage yang dilakukan adalah dengan menggunakan RecyclerView untuk menampilkan match-match di main activity. Berikut ini adalah gambar dari memory profiler sebelum dan setelah dilakukan optimisasi:

![Sebelum Optimisasi](/mem-before.png)

Dapat dilihat pada gambar di atas bahwa memory yang digunakan saat aplikasi pertama kali dijalankan total berjumlah sekitar 55.3 MB saat sebelum menggunakan Recycler View.

![Setelah Optimisasi](/mem-after.png)

Lalu saat menggunakan Recycler View, memory yang digunakan saat awal aplikasi dijalankan berkurang, sehingga total memory yang digunakan berjumlah sekitar 44.4 MB.