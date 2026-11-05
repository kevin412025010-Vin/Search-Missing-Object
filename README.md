# Search-Missing-Object
Game mencari barang hilang &amp; mengurutkan barang 


Judul : Cari Barang yang Hilang

---
Deskripsi Singkat

Cari Barang yang Hilang adalah game edukasi berbasis Cognitive Science yang dirancang untuk melatih kemampuan kognitif pemain, khususnya memori, pengenalan pola, dan kemampuan mengingat urutan.

Pemain akan diberikan beberapa objek yang harus diperhatikan dan diingat dalam waktu tertentu. Setelah itu, salah satu objek dapat dihilangkan atau urutan objek dapat diacak. Pemain kemudian harus menentukan jawaban yang benar berdasarkan informasi yang telah mereka ingat.

Game memiliki 3 tingkat kesulitan, yaitu Easy, Normal, dan Hard. Setiap tingkat kesulitan terdiri dari 5 tahapan dengan tingkat tantangan yang semakin meningkat.

---
Author

- 412025010 + Kevin Stevanus
- 412025005 + Juan Raffles
- 412025006 + Michael Valentino
- 412025031 + Elfrido

---
Project Structure


---
Fitur-Fitur

1. Main Menu

Menu utama menjadi halaman awal permainan yang menyediakan beberapa pilihan:

- Start
- Setting
- Exit

Ketika pemain menekan Start, pemain dapat memilih tingkat kesulitan permainan.

2. Difficulty

Game memiliki 3 tingkat kesulitan:

🟢 Easy
- Cocok untuk pemain yang baru mulai.
- Jumlah objek lebih sedikit.
- Waktu untuk mengingat objek lebih lama.
- Pola permainan lebih sederhana.

🟡 Normal
- Jumlah objek lebih banyak.
- Waktu mengingat lebih singkat.
- Tantangan memori meningkat.

🔴 Hard
- Jumlah objek lebih banyak.
- Waktu mengingat lebih singkat.
- Urutan objek dapat diacak.
- Pemain harus mengingat objek sekaligus posisinya.

Setiap difficulty memiliki 5 tahapan (stage).

---
Sistem Tahapan

- Tahapan 1 — Missing Object

Pemain diberikan beberapa objek yang harus diingat.

Contoh:

```
🚗  🍎  🐈  🎶
```

Pemain diberikan waktu sekitar 5–10 detik untuk mengingat objek.

Setelah waktu habis, salah satu objek akan menghilang.

```
🚗  🍎  ?  🎶
```

Kemudian pemain diberikan beberapa pilihan:

```
🐈  🐕  👻  💀
```

Pemain harus memilih objek yang hilang.

Jika jawaban benar:

```
🚗  🍎  🐈  🎶
```

Objek akan ditampilkan kembali dan pemain mendapatkan poin.

---
- Tahapan 2 — More Objects

Mekanisme hampir sama dengan Tahapan 1, tetapi jumlah objek ditambah menjadi **6 objek**.

Contoh:

```
🚗  🍎  🐈  🎶  ⚽  🌳
```

Setelah waktu mengingat selesai, salah satu objek dihilangkan.

```
🚗  🍎  🐈  ?  ⚽  🌳
```

Pemain kemudian memilih objek yang hilang dari beberapa pilihan.

Tujuannya adalah meningkatkan kemampuan working memory pemain.

---
- Tahapan 3 — More Objects

Pada tahap ini jumlah objek ditingkatkan menjadi 8 objek.

Contoh:

```
🚗 🍎 🐈 🎶 ⚽ 🌳 🐕 🚲
```

Setelah beberapa detik:

```
🚗 🍎 🐈 🎶 ? 🌳 🐕 🚲
```

Pemain harus mengingat objek yang hilang.

Tahap ini memiliki tingkat kesulitan lebih tinggi karena pemain harus mengingat lebih banyak informasi dalam waktu yang terbatas.

---
- Tahapan 4 — Random Order

Pada tahap ini bukan hanya objek yang harus diingat, tetapi juga **urutan objek**.

Informasi awal:

```
🚗 🍎 🐈 🎶
```

Setelah itu objek diacak:

```
🎶 🚗 🐈 🍎
```

Pemain harus menentukan kembali urutan yang benar.

Jawaban pemain:

```
🚗 🍎 🐈 🎶
```

Jika urutan sesuai dengan urutan awal, pemain mendapatkan poin.

Tahap ini melatih kemampuan ngasah memori.

---
- Tahapan 5 — Combination

Tahap terakhir merupakan gabungan dari mekanisme sebelumnya.

Pemain dapat menghadapi dua jenis tantangan:

Missing Object

``` 
🚗 🍎 🐈 🎶 ⚽ 🌳
```

Menjadi:

```
🚗 🍎 ? 🎶 ⚽ 🌳
```

Pemain harus mencari objek yang hilang.

Random Order

```
🚗 🍎 🐈 🎶
```

Menjadi:

```
🎶 🐈 🚗 🍎
```

Pemain harus mengembalikan urutan objek seperti semula.

Dengan demikian, pemain harus mampu mengingat **objek sekaligus urutannya**.

---
- Sistem Point

Game menggunakan sistem skor untuk memberikan feedback kepada pemain.

| Kondisi               | Point |
| --------------------- | ------|
| Jawaban benar         |  +10  |
| Jawaban salah         |   -5  |
| Menjawab dengan cepat |   +5  |
| Menggunakan Hint      |   -5  |

---
- Sistem Hint

Pemain dapat menggunakan Hint ketika mengalami kesulitan.

Namun, penggunaan Hint akan mengurangi 5 poin.

Sistem ini mendorong pemain untuk mencoba mengandalkan kemampuan memorinya terlebih dahulu.

---
- Setting

Menu Setting menyediakan beberapa pengaturan:

Volume
Pemain dapat mengatur volume suara game.

Bahasa
Game menyediakan dua pilihan bahasa:
🇮🇩 Bahasa Indonesia
🇬🇧 English

Pengaturan bahasa akan mengubah teks yang terdapat pada menu dan permainan.

---
- Alur Permainan
```
MAIN MENU
    │
    ├── START
    │     │
    │     └── PILIH DIFFICULTY
    │             │
    │             ├── EASY
    │             │    ├── Stage 1
    │             │    ├── Stage 2
    │             │    ├── Stage 3
    │             │    ├── Stage 4
    │             │    └── Stage 5
    │             │
    │             ├── NORMAL
    │             │    ├── Stage 1
    │             │    ├── Stage 2
    │             │    ├── Stage 3
    │             │    ├── Stage 4
    │             │    └── Stage 5
    │             │
    │             └── HARD
    │                  ├── Stage 1
    │                  ├── Stage 2
    │                  ├── Stage 3
    │                  ├── Stage 4
    │                  └── Stage 5
    │
    ├── SETTING
    │     ├── Volume
    │     └── Bahasa
    │
    └── EXIT
```
---
- Konsep Cognitive Science

Game ini menerapkan beberapa konsep dari **Cognitive Science**, yaitu:

1. Working Memory

Pemain harus menyimpan informasi objek dalam waktu singkat sebelum menjawab pertanyaan.

2. Attention

Pemain harus memperhatikan objek yang ditampilkan karena informasi tersebut tidak akan ditampilkan terus-menerus.

3. Visual Memory

Pemain menggunakan ingatan visual untuk mengenali objek yang sebelumnya ditampilkan.

4. Sequence Memory

Pada tahap tertentu, pemain harus mengingat dan menyusun kembali urutan objek.

5. Pattern Recognition

Pemain mengenali objek dan membandingkan kondisi sebelum dan sesudah perubahan.

---
- Tujuan Game

Tujuan utama game adalah memberikan permainan sederhana yang dapat melatih kemampuan kognitif pemain melalui aktivitas:

- Mengingat & Mengenali objek
- Mengingat urutan
- Mengambil keputusan dengan cepat
- Memecahkan masalah berdasarkan informasi yang diingat

Semakin tinggi tingkat kesulitan, semakin banyak informasi yang harus diproses oleh pemain.
