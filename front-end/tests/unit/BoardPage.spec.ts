import { VueWrapper, mount } from '@vue/test-utils'
import { vi, describe, beforeEach, afterEach, it, expect } from 'vitest'
import { createTestingPinia } from '@pinia/testing'

import { useBoardUserStore } from '@/stores/useBoardUserStore'
import { i18n } from '@/locales'
import router from '@/router'

import BoardPage from '@/views/BoardPage.vue'

// vi.mock('@/services/me')
describe('BoardPage', () => {
    let wrapper: VueWrapper<any>
    let store: ReturnType<typeof useBoardUserStore>

    beforeEach(() => {
        wrapper = mount(BoardPage, {
            global: {
                plugins: [createTestingPinia({ createSpy: vi.fn, stubActions: true }), i18n, router],
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

    it('정상적으로 렌더링합니다.', () => {
        expect(wrapper.element);
    });

    it('멤버 추가 완료 시 멤버를 추가합니다.', async () => {
        const newMember = { id: 1, shortName: 'John Doe' };
        await wrapper.vm.onMemberAdded(newMember);
        expect(wrapper.vm.members).toContain(newMember);
    });
})