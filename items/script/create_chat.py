import random
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
from google.cloud.firestore import SERVER_TIMESTAMP

cred = credentials.Certificate("comp6442-e1d42-firebase-adminsdk-blpir-bfcfaafbe6.json")
firebase_admin.initialize_app(cred)
db = firestore.client()
docs = db.collection('user').stream()


def get_user_list():
    user_id = [doc.id for doc in docs]
    return user_id


users = get_user_list()
# print(users)
article ="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam in scelerisque sem. Maurisvolutpat, dolor id interdum ullamcorper, risus dolor egestas lectus, sit amet mattis purusdui nec risus. Maecenas non sodales nisi, vel dictum dolor. Class aptent taciti sociosqu adlitora torquent per conubia nostra, per inceptos himenaeos. Suspendisse blandit eleifenddiam, vel rutrum tellus vulputate quis. Aliquam eget libero aliquet, imperdiet nisl a,ornare ex. Sed rhoncus est ut libero porta lobortis. Fusce in dictum tellus.\n\nSuspendisse interdum ornare ante. Aliquam nec cursus lorem. Morbi id magna felis. Vivamusegestas, est a condimentum egestas, turpis nisl iaculis ipsum, in dictum tellus dolor sedneque. Morbi tellus erat, dapibus ut sem a, iaculis tincidunt dui. Interdum et malesuadafames ac ante ipsum primis in faucibus. Curabitur et eros porttitor, ultricies urna vitae,molestie nibh. Phasellus at commodo eros, non aliquet metus. Sed maximus nisl nec dolorbibendum, vel congue leo egestas.\n\nSed interdum tortor nibh, in sagittis risus mollis quis. Curabitur mi odio, condimentum sitamet auctor at, mollis non turpis. Nullam pretium libero vestibulum, finibus orci vel,molestie quam. Fusce blandit tincidunt nulla, quis sollicitudin libero facilisis et. Integerinterdum nunc ligula, et fermentum metus hendrerit id. Vestibulum lectus felis, dictum atlacinia sit amet, tristique id quam. Cras eu consequat dui. Suspendisse sodales nunc ligula,in lobortis sem porta sed. Integer id ultrices magna, in luctus elit. Sed a pellentesqueest.\n\nAenean nunc velit, lacinia sed dolor sed, ultrices viverra nulla. Etiam a venenatis nibh.Morbi laoreet, tortor sed facilisis varius, nibh orci rhoncus nulla, id elementum leo duinon lorem. Nam mollis ipsum quis auctor varius. Quisque elementum eu libero sed commodo. Ineros nisl, imperdiet vel imperdiet et, scelerisque a mauris. Pellentesque varius ex nunc,quis imperdiet eros placerat ac. Duis finibus orci et est auctor tincidunt. Sed non viverraipsum. Nunc quis augue egestas, cursus lorem at, molestie sem. Morbi a consectetur ipsum, aplacerat diam. Etiam vulputate dignissim convallis. Integer faucibus mauris sit amet finibusconvallis.\n\nPhasellus in aliquet mi. Pellentesque habitant morbi tristique senectus et netus etmalesuada fames ac turpis egestas. In volutpat arcu ut felis sagittis, in finibus massagravida. Pellentesque id tellus orci. Integer dictum, lorem sed efficitur ullamcorper,libero justo consectetur ipsum, in mollis nisl ex sed nisl. Donec maximus ullamcorpersodales. Praesent bibendum rhoncus tellus nec feugiat. In a ornare nulla. Donec rhoncuslibero vel nunc consequat, quis tincidunt nisl eleifend. Cras bibendum enim a justo luctusvestibulum. Fusce dictum libero quis erat maximus, vitae volutpat diam dignissim."


def get_friend(id):
    user = db.collection('user').document(id).get()
    friend_list = user.to_dict()['friendList']
    friend = random.sample(friend_list, 1)
    return friend


def add_friend():
    user1, user2 = random.sample(users, 2)
    user1_ref = db.collection('user').document(user1)
    user2_ref = db.collection('user').document(user2)
    batch = db.batch()
    batch.update(user1_ref, {'friendList': firestore.ArrayUnion([user2])})
    batch.update(user2_ref, {'friendList': firestore.ArrayUnion([user1])})
    batch.commit()
    for i in range(5):
        create_chat(user1, user2)
        create_chat(user2, user1)
    print("Friends added successfully: {} <-> {}".format(user1, user2))


def create_chat(user1, user2):
    sender_id = user1
    receiver_id = user2
    min_length, max_length = 10, 30
    length = random.randint(min_length, min_length + max_length)
    start_index = random.randint(0, len(article) - length)
    message = article[start_index:start_index + length]
    message_data = {
        'senderId': sender_id,
        'receiverId': receiver_id,
        'message': message,
        'time': SERVER_TIMESTAMP
    }
    db.collection('collection_chat').add(message_data)


for i in range(50):
    add_friend()
'''
for i in range(10):
    create_chat()
'''
docs1 = list(db.collection('collection_chat').stream())
print(len(docs1))


