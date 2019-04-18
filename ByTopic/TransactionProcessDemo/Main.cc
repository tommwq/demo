const double MessageFailureProbability = 1e-5;
const double MessageDuplicateProbability = 1e-5;

const unsigned long ValueSize = 8192;
using Value = char[ValueSize];

struct Message {
    bool status;
    Message *next;
    Value value;
};

using Process_id = unsigned long;

bool message_send(Process_id target, Value value);
bool message_get(Value *value, bool *status);


bool get_message(Value *value) {
    bool message_status = false;
    bool message_exist = false;

    // 直到读取一个消息或消息队列为空。
    while (!message_status) {
        message_exist = message_get(value, &message_status);
        // 消息队列为空，退出循环。
        if (!message_exist) {
            break;
        }
    }

    return message_exist;
}

const unsigned long MaxProcessNumber = 100000;
using Process_id = unsigned long;

struct Process_state {
    char program[ValueSize];
    char data[ValueSize];
};

struct Process {
    Process_state initial_state;
    Process_state current_state;
    Message* message_list;
};


int main() {
}
