import { VueWrapper, mount } from '@vue/test-utils'
import { vi, describe, beforeEach, afterEach, it, expect } from 'vitest'
import { createTestingPinia } from '@pinia/testing'
// import cardListsService from '@/services/card-lists'
import { useBoardUserStore } from '@/stores/useBoardUserStore'
import { i18n } from '@/locales'
import router from '@/router'

import BoardPage from '@/views/BoardPage.vue'
import { nextTick } from 'vue'

import { vuetify } from '@/vuetify'

vi.mock('@/services/card-lists')
describe('BoardPage', () => {
    let wrapper: VueWrapper<any>
    let store: ReturnType<typeof useBoardUserStore>

    beforeEach(() => {
        wrapper = mount(BoardPage, {
            global: {
                plugins: [createTestingPinia({ createSpy: vi.fn, stubActions: true }), i18n, router, vuetify],
                mocks: {
                    t: (msg: string) => msg
                },
                stubs: { FontAwesomeIcon: true }
            }
        })
        store = useBoardUserStore()

    })

    afterEach(() => {
        wrapper.unmount()
    })

    // it('카드 추가하기 버튼 클릭 시 입력폼을 활성화합니다.', async () => {
    //     const pageElement = wrapper.find('.add-card-button');
    //     await pageElement.trigger('click');

    //     const cardForm = wrapper.find('.add-card-form');

    //     expect(cardForm.exists()).toBe(true);
    // })

    it('리스트 추가하기 버튼 클릭 시 입력폼을 활성화합니다.', async () => {
        const pageElement = wrapper.find('.add-list-button');
        await pageElement.trigger('click');

        const listForm = wrapper.find('.add-list-form');

        expect(listForm.isVisible()).toBe(true);
    })

    // it('입력폼을 제외한 공간을 클릭하면 활성화된 입력폼을 닫습니다.', async () => {

    //     const cardForm = wrapper.find('.add-card-form');
    //     const listForm = wrapper.find('.add-list-form');

    //     const buttonElement = wrapper.find('.add-card-button');
    //     await buttonElement.trigger('click');

    //     const pageElement = wrapper.find('.page');
    //     await pageElement.trigger('click');

    //     expect(cardForm.exists()).toBe(false);
    //     expect(listForm.isVisible()).toBe(false);
    // })

    it('추가하기 버튼 클릭 시 카드 리스트 추가하기', async () => {
        // 입력 창에 값 설정
        await wrapper.find({ ref: 'focusListInput' }).setValue('New List')
        expect((wrapper.find({ ref: 'focusListInput' }).element as HTMLInputElement).value).toBe("New List");

        // 카드 추가 메서드 동작
        wrapper.vm.addCardList()

        // 카드 추가 후 값 확인
        nextTick(() => {
            expect(wrapper.vm.addListForm.name).toBe('')
            expect(wrapper.vm.cardLists).toHaveProperty('0.name', 'New List')
        })
    })
})
