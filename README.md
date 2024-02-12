# Tugas Kecil I IF2211 - Breach Protocol

## Identitas
Kristo Anugrah          (13522024)

## Deskripsi Program
Program berbasis GUI yang mensimulasikan puzzle Breach Protocol pada game Cyberpunk 2077. Program mencari solusi yang optimal dengan algoritma Brute Force. Pengguna dapat membuat puzzle secara acak, memasukan input dari file .txt, atau memasukan input manual dari GUI.

Direktori tugas kecil ini memiliki struktur file sebagai berikut:
```shell
.
│   README.md
│
├───bin                                 # Hasil compile file java (.class)
│   └───bruteforce
│          gui.class
│          working.class
│          ...  
├───doc                                 # Laporan tugas kecil (dokumentasi)
├───src                                 # Source code tugas kecil
│   └───bruteforce
│          gui.java
│          working.java
└───assets                              # Aset program (.jpg, .ttf)
│       BoxedSemibold.ttf
│       bpprotocol.jpg
│       buffersq.png
└───test                                # Solusi jawaban data uji
        test1.txt
        test2.txt
        ...
```

## Preview
![alt text](/results/previewguisolver.png)
![alt text](/results/previewguisolved.png)

## Dependensi
* Java 19.0.2 ke atas
* Window berukuran setidaknya 1024 x 768
* 250 MB RAM

## Cara Menjalankan Program
1. Download ZIP atau clone repository
2. Jalankan program terminal (misalnya Command Prompt, Powershell, dan semacamnya).
3. Pindah current directory ke folder Tucil1_13522024, yaitu `(folder download)\Tucil1_13522024`.
4. Jalankan command `java -cp bin bruteforce.gui` untuk memulai program.
