import firebase from "./configuration-firebase-thien.js"

const uploadedURLs = [];
const uploadedURLs1 = [];
const uploadedURLs2 = [];
const uploadedURLs3 = [];
const uploadedURLs4 = [];
const uploadedURLs5 = [];

async function handleUpload(e) {

    console.dir(e);
    const ref = firebase.storage().ref();
    const files = e.target.files;

    for (const file of files) {
        const name = +new Date() + "-" + file.name;
        const metadata = {
            contentType: file.type
        };

        try {
            const snapshot = await ref.child(name).put(file, metadata);
            const url = await snapshot.ref.getDownloadURL();

            console.log(url);
            uploadedURLs.push(url);

            if (uploadedURLs.length === files.length) {
                alert('Tất cả ảnh đã được tải lên thành công');
                document.getElementById("imageAvatar").value = uploadedURLs[0];
                // document.getElementById("imageLicense1").value = uploadedURLs[1];
                // document.getElementById("imageLicense2").value = uploadedURLs[2];
                // document.getElementById("image1").value = uploadedURLs[3];
                // document.getElementById("image2").value = uploadedURLs[4];
            }
        } catch (error) {
            console.error(error);
        }
    }
}
async function handleUpload1(e) {
    console.dir(e);
    const ref = firebase.storage().ref();
    const files = e.target.files;

    for (const file of files) {
        const name = +new Date() + "-" + file.name;
        const metadata = {
            contentType: file.type
        };

        try {
            const snapshot = await ref.child(name).put(file, metadata);
            const url = await snapshot.ref.getDownloadURL();

            console.log(url);
            uploadedURLs1.push(url);

            if (uploadedURLs1.length === files.length) {
                alert('Tất cả ảnh đã được tải lên thành công');
                document.getElementById("imageLicense1").value = uploadedURLs1[0];
            }
        } catch (error) {
            console.error(error);
        }
    }
}
async function handleUpload2(e) {
    console.dir(e);
    const ref = firebase.storage().ref();
    const files = e.target.files;

    for (const file of files) {
        const name = +new Date() + "-" + file.name;
        const metadata = {
            contentType: file.type
        };

        try {
            const snapshot = await ref.child(name).put(file, metadata);
            const url = await snapshot.ref.getDownloadURL();

            console.log(url);
            uploadedURLs2.push(url);

            if (uploadedURLs2.length === files.length) {
                alert('Tất cả ảnh đã được tải lên thành công');
                document.getElementById("imageLicense2").value = uploadedURLs2[0];
            }
        } catch (error) {
            console.error(error);
        }
    }
}
async function handleUpload3(e) {
    console.dir(e);
    const ref = firebase.storage().ref();
    const files = e.target.files;

    for (const file of files) {
        const name = +new Date() + "-" + file.name;
        const metadata = {
            contentType: file.type
        };

        try {
            const snapshot = await ref.child(name).put(file, metadata);
            const url = await snapshot.ref.getDownloadURL();

            console.log(url);
            uploadedURLs3.push(url);

            if (uploadedURLs3.length === files.length) {
                alert('Tất cả ảnh đã được tải lên thành công');
                document.getElementById("image1").value = uploadedURLs3[0];
            }
        } catch (error) {
            console.error(error);
        }
    }
}
async function handleUpload4(e) {
    console.dir(e);
    const ref = firebase.storage().ref();
    const files = e.target.files;

    for (const file of files) {
        const name = +new Date() + "-" + file.name;
        const metadata = {
            contentType: file.type
        };

        try {
            const snapshot = await ref.child(name).put(file, metadata);
            const url = await snapshot.ref.getDownloadURL();

            console.log(url);
            uploadedURLs4.push(url);

            if (uploadedURLs4.length === files.length) {
                alert('Tất cả ảnh đã được tải lên thành công');
                document.getElementById("image2").value = uploadedURLs4[0];
            }
        } catch (error) {
            console.error(error);
        }
    }
}
async function handleUpload5(e) {
    console.dir(e);
    const ref = firebase.storage().ref();
    const files = e.target.files;

    for (const file of files) {
        const name = +new Date() + "-" + file.name;
        const metadata = {
            contentType: file.type
        };

        try {
            const snapshot = await ref.child(name).put(file, metadata);
            const url = await snapshot.ref.getDownloadURL();

            console.log(url);
            uploadedURLs5.push(url);

            if (uploadedURLs5.length === files.length) {
                alert('Tất cả ảnh đã được tải lên thành công');
                document.getElementById("image6").value = uploadedURLs5[0];
            }
        } catch (error) {
            console.error(error);
        }
    }
}



document.getElementById("upload-file").addEventListener("change", function (e) {
    handleUpload(e)
});
document.getElementById("upload-file1").addEventListener("change", function (e) {
    handleUpload1(e)
});
document.getElementById("upload-file2").addEventListener("change", function (e) {
    handleUpload2(e)
});
document.getElementById("upload-file3").addEventListener("change", function (e) {
    handleUpload3(e)
});
document.getElementById("upload-file4").addEventListener("change", function (e) {
    handleUpload4(e)
});
document.getElementById("upload-file5").addEventListener("change", function (e) {
    handleUpload5(e)
});





