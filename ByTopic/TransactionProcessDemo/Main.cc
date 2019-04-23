#include <vector>
#include <iostream>
#include <random>
#include <chrono>
#include <thread>
#include <algorithm>

std::random_device random_device{};
std::mt19937 generator{random_device()};
std::uniform_real_distribution<> distribution{0, 1};

double random() {
    return distribution(generator);
}

double time() {
    return std::chrono::duration_cast<std::chrono::seconds>(std::chrono::steady_clock::now().time_since_epoch()).count();
}

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

const double ProcessMTTF = 1e7;
const double ProcessMTTR = 1e4;

void process_execution(Process_id pid) {
    unsigned long processFailureTime;
    unsigned long processRepairTime;
    Message *message, *next_message;

    while (true) {
        processFailureTime = time() - log(random()) * ProcessMTTF;
        processRepairTime = -log(random()) * ProcessMTTR;
        while (time() < processFailureTime) {
            execute(processs[pid].current_state);
        }

        wait(processRepairTime);
        memcpy(process[pid].current_state, process[pid].initial_state, ValueSize);
        while (message_get(message, status)) {}
    }
}

void checkpoint_restart_process() {
    unsigned long disk = 0;
    unsigned long address[2] = {0, 1};
    unsigned long index;
    struct {
        unsigned long ticket_number;
        char filter[ValueSize];
    } value[2];
    struct {
        Process_id pid;
        char filter[ValueSize];
    } message;

    // 读取两个状态，选择新状态
    for (index = 0; index < 2; index++) {
        if (!reliable_read(disk, address[index], value[index])) {
            panic();
        }
    }

    index = 1;
    if (value[0].ticket_number < value[1].ticket_number) {
        index = 0;
        memcpy(value[0], value[1], ValueSize);
    }

    while (true) {
        // 等待消息。
        while (!get_msg(&message)) {};
        value[0].ticket_number = value[0].ticket_number + 1;
        if (!reliable_write(disk, address[index], value[0])) {
            panic();
        }
        index = (index + 1) % 2;
        message_send(message.pid, value[0]);
    }
}

void persistent_process() {
    wait_to_be_primary();
    while (true) {
        begin_work();
        read_request();
        do_it();
        commit_work();
        reply();
    }
}

int main() {
}

void persistent_ticket_server() {
    int ticket_number;
    struct {
        Process_id him;
        char filter[ValueSize];
    } message;

    wait_to_be_primary();
    while (true) {
        begin_work();
        while (!get_message(&message)) {}
        exec_sql("UPDATE ticket SET ticket_number=ticket_number+1");
        ticket_number = exec_sql("SELECT MAX(ticket_number) FROM ticket");
        commit_work();
        message_send(message.him, value);
    }
}

bool message_send(Process_id him, Value value) {
    Message *message;
    Message *message_queue;

    if (him > MaxProcessNumber) {
        return false;
    }

    while (true) {
        message = (Message*) malloc(sizeof(Message));
        message->status = true;
        message->next = nullptr;
        memcpy(message->value, value, ValueSize);
        message_queue = process[him].messages;
        if (message_queue == nullptr) {
            process[him].messages = message;
        } else {
            while (message_queue->next != nullptr) {
                message_queue = message_queue->next;
            }
            message_queue->next = message;
        }

        // 模拟消息损坏
        if (random() < MessageFailureProbability) {
            message->status = false;
        }

        // 模拟消息重复
        if (random() < MessageDuplicateProbability) {
            continue;
        }

        break;
    }

    return true;
}

bool message_get(Value *value, bool *status) {
    Process_id self = current_pid();
    Message *message;
    message = process[self].messages;
    if (message == nullptr) {
        return false;
    }

    process[self].messages = message->next;
    *status = message->status;
    memcpy(value, message->value, ValueSize);
    free(message);
    return true;
}
