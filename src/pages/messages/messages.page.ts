import { expect, type Locator, type Page } from '@playwright/test';

export interface MessageSummary {
  readonly subject: string;
  readonly date: string;
}

export class MessagesPage {
  public constructor(private readonly page: Page) {}

  public async open(): Promise<void> {
    await this.messagesLink.click();
    await expect(this.unreadTab).toBeVisible();
  }

  /** Lee asunto y fecha del primer mensaje de la bandeja Recibidos, que identifican a ese mensaje. */
  public async readFirstMessage(): Promise<MessageSummary> {
    await expect(this.firstMessage).toBeVisible();
    return {
      subject: (await this.firstMessage.locator('p').nth(1).innerText()).trim(),
      date: (await this.firstMessage.locator('p').nth(2).innerText()).trim(),
    };
  }

  /** Elimina el primer mensaje y espera a que la bandeja deje de mostrarlo en primer lugar. */
  public async deleteFirstMessage(): Promise<void> {
    const messageText = await this.firstMessage.innerText();

    await this.firstMessage.getByRole('button').click();
    await expect(this.deleteAction).toBeVisible();
    await this.deleteAction.click();

    await expect(this.deleteAction).toBeHidden();
    await expect(this.firstMessage, 'La bandeja debe dejar de mostrar el mensaje eliminado.').not.toHaveText(
      messageText,
    );
  }

  public async ensureReadMessageAvailable(): Promise<void> {
    await this.readTab.click();

    if ((await this.firstMessage.count()) === 0) {
      await this.unreadTab.click();
      await expect(this.firstMessage).toBeVisible();
      await this.firstMessage.getByRole('button').click();
      await expect(this.markAsReadAction).toBeVisible();
      await this.markAsReadAction.click();
      await this.readTab.click();
    }

    await expect(this.firstMessage).toBeVisible();
  }

  public async markFirstReadMessageAsUnread(): Promise<string> {
    await this.readTab.click();
    await expect(this.firstMessage).toBeVisible();
    const messageSubject = (await this.firstMessage.locator('p').nth(1).innerText()).trim();

    if (!messageSubject) {
      throw new Error('El primer mensaje leído no contiene asunto para identificarlo.');
    }

    await this.firstMessage.getByRole('button').click();
    await expect(this.markAsUnreadAction).toBeVisible();
    await this.markAsUnreadAction.click();
    return messageSubject;
  }

  public async readUnreadMessageSubjects(): Promise<readonly string[]> {
    await this.homeButton.click();
    await expect(this.messagesLink).toBeVisible();
    await this.messagesLink.click();
    await expect(this.unreadTab).toBeVisible();
    await this.unreadTab.click();
    await expect(this.messageList).toBeVisible();

    const messages = this.messageList.locator('> div');
    const subjects: string[] = [];
    for (let index = 0; index < (await messages.count()); index += 1) {
      const subject = (await messages.nth(index).locator('p').nth(1).innerText()).trim();
      if (subject) {
        subjects.push(subject);
      }
    }
    return subjects;
  }

  public async restoreMessageAsRead(messageSubject: string): Promise<void> {
    await this.unreadTab.click();
    const message = this.messageBySubject(messageSubject);
    await expect(message).toBeVisible();
    await message.getByRole('button').click();
    await expect(this.markAsReadAction).toBeVisible();
    await this.markAsReadAction.click();
  }

  private get deleteAction(): Locator {
    return this.page.getByText('Eliminar', { exact: true });
  }

  private get firstMessage(): Locator {
    return this.messageList.locator('> div').first();
  }

  private get homeButton(): Locator {
    return this.page.getByRole('button', { name: 'Inicio', exact: true });
  }

  private get markAsUnreadAction(): Locator {
    return this.page.getByText('Marcar como no leído', { exact: true });
  }

  private get markAsReadAction(): Locator {
    return this.page.getByText('Marcar como leído', { exact: true });
  }

  private get messageList(): Locator {
    return this.page.locator('ul.message-list');
  }

  private messageBySubject(messageSubject: string): Locator {
    return this.messageList.locator('> div').filter({ hasText: messageSubject }).first();
  }

  private get messagesLink(): Locator {
    return this.page.getByRole('link', { name: /^Mensajes/ });
  }

  private get readTab(): Locator {
    return this.page.locator('#message_read_icon');
  }

  private get unreadTab(): Locator {
    return this.page.locator('#message_unread_icon');
  }
}
