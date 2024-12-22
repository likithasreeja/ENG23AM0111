#include <stdio.h>
#include <stdlib.h>

// Node structure
struct Node {
    int data;
    struct Node* next;
};

// Create a new node
struct Node* createNode(int value) {
    struct Node* newNode = (struct Node*)malloc(sizeof(struct Node));
    newNode->data = value;
    newNode->next = NULL;
    return newNode;
}

// Insert a node at the beginning of the list
struct Node* insertAtBeginning(struct Node* head, int value) {
    struct Node* newNode = createNode(value);

    if (head == NULL) {
        newNode->next = newNode;
        return newNode;
    }

    newNode->next = head->next;
    head->next = newNode;
    head=newNode;
    return head;
}

// Insert a node at the end of the list
struct Node *insertAtEnd(struct Node *head, int value) {
    struct Node *newNode = createNode(value);

    if (head == NULL) {
        newNode->next = newNode;
        return newNode;
    }

    struct Node *temp = head;
    while (temp->next != head) {
        temp = temp->next;
    }

    temp->next = newNode;
    newNode->next = head;
    return head;
}


// Delete a node with given value
struct Node* deleteNode(struct Node* head, int value) {
    if (head == NULL) {
        printf("List is empty.\n");
        return NULL;
    }

    struct Node* current = head;
    struct Node* prev = NULL;

    do {
        if (current->data == value) {
            if (prev != NULL) {
                prev->next = current->next;
                free(current);
                return head;
            } else {
                struct Node* temp = head;
                while (temp->next != head) {
                    temp = temp->next;
                }
                temp->next = head->next;
                free(head);
                return temp->next;
            }
        }

        prev = current;
        current = current->next;
    } while (current != head);

    printf("Value not found in the list.\n");
    return head;
}

// Search for a node with given value
void search(struct Node* head, int value) {
    if (head == NULL) {
        printf("List is empty.\n");
        return;
    }

    struct Node* current = head;
    int found = 0;
    int position = 0;

    do {
        position++;
        if (current->data == value) {
            printf("Value %d found at position %d.\n", value, position);
            found = 1;
        }
        current = current->next;
    } while (current != head);

    if (!found) {
        printf("Value not found in the list.\n");
    }
}

// Display the circular linked list
void display(struct Node* head) {
    if (head == NULL) {
        printf("List is empty.\n");
        return;
    }

    struct Node* current = head;

    printf("Circular Linked List: ");
    do {
        printf("%d ", current->data);
        current = current->next;
    } while (current != head);

    printf("\n");
}

// Update a node with given value
void update(struct Node* head, int oldValue, int newValue) {
    if (head == NULL) {
        printf("List is empty.\n");
        return;
    }

    struct Node* current = head;
    int found = 0;

    do {
        if (current->data == oldValue) {
            current->data = newValue;
            printf("Value updated successfully.\n");
            found = 1;
        }
        current = current->next;
    } while (current != head);

    if (!found) {
        printf("Value not found in the list.\n");
    }
}

// Free memory allocated for the circular linked list
void freeList(struct Node* head) {
    if (head == NULL) {
        return;
    }

    struct Node* current = head;
    struct Node* temp = NULL;

    do {
        temp = current;
        current = current->next;
        free(temp);
    } while (current != head);
}

int main() {
    struct Node* head = NULL;
    int choice, value, oldValue, newValue;

    do {
        printf("\nCircular Linked List Operations:\n");
        printf("1. Insert at Beginning\n");
        printf("2. Insert at End\n");
        printf("3. Delete a Node\n");
        printf("4. Search for a Node\n");
        printf("5. Display the List\n");
        printf("6. Update a Node\n");
        printf("7. Exit\n");
        printf("Enter your choice: ");
        scanf("%d", &choice);

        switch (choice) {
            case 1:
                printf("Enter value to insert at the beginning: ");
                scanf("%d", &value);
                head = insertAtBeginning(head, value);
                break;
            case 2:
                printf("Enter value to insert at the end: ");
                scanf("%d", &value);
                head = insertAtEnd(head, value);
                break;
            case 3:
                printf("Enter value to delete: ");
                scanf("%d", &value);
                head = deleteNode(head, value);
                break;
            case 4:
                printf("Enter value to search: ");
                scanf("%d", &value);
                search(head, value);
                break;
            case 5:
                display(head);
                break;
            case 6:
                printf("Enter value to update old value: ");
                scanf("%d", &oldValue);
                printf("Enter new value: ");
                scanf("%d", &newValue);
                update(head, oldValue, newValue);
                break;
            case 7:
                freeList(head);
                printf("Exiting...\n");
                break;
            default:
                printf("Invalid choice! Please enter a valid choice.\n");
        }
    } while (choice != 7);

    return 0;
}
